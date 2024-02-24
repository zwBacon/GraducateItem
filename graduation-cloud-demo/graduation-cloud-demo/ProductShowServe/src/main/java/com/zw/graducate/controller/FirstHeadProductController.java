package com.zw.graducate.controller;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zw.graducate.clients.UserClient;
import com.zw.graducate.entity.*;
import com.zw.graducate.interfaceAop.CustomerAopNote;
import com.zw.graducate.service.FirstHeadProductService;
import com.zw.graducate.service.ProductTagService;
import com.zw.graducate.service.SecPlusRentProductDetailService;
import com.zw.graducate.service.impl.FirstHeadProductServiceImpl;
import com.zw.graducate.utils.EsHandelUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/1 13:40
 */

@RestController
@RequestMapping("/productServe")
@CrossOrigin(origins = {"*", "null"})
public class FirstHeadProductController {


    @Autowired
    private FirstHeadProductService service;

    @Autowired
    private SecPlusRentProductDetailService secPlusRentProductDetailService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductTagService tagService;

    @Autowired
    private RestHighLevelClient esClient;


//    查询数据库所有数据并写入es
     @GetMapping("/selectAllProductByPageAndUnitES")
     public String selectAllProductByPageAndUnitES(Integer beginNum, Integer size,String city) throws IOException {
          IPage<FirstHeadProductDTO> firstHeadProductDTOpage = service.selectAllProductByPageNew(beginNum, size,city);
         List<FirstHeadProductDTO> records = firstHeadProductDTOpage.getRecords();
         for(FirstHeadProductDTO dto : records) {
//             写入
//          准备Request对象
             IndexRequest request = new IndexRequest("all_product_infos").id(dto.getID());
//         准备json数据
             request.source(JSON.toJSONString(dto), XContentType.JSON);
//         发送请求
             esClient.index(request, RequestOptions.DEFAULT);
         }
//           读取
         GetRequest request2 = new GetRequest("all_product_infos","91");
         GetResponse response = esClient.get(request2, RequestOptions.DEFAULT);
         String json = response.getSourceAsString();
         return json;
     }


    //     根据productName查询es并做处理
    @GetMapping("/selectAllProductByConditionOfPageAndUnitES")
    public Map<String,Object> selectAllProductByConditionOfPageAndUnitES(String productName,Integer currentPage,Integer size,String type) throws IOException {

        Map<String,Object> result = new HashMap<>();
        List<FirstHeadProductDTO> headProductDTOList = new ArrayList<>();

    //    match查询
        SearchRequest request = new SearchRequest("all_product_infos");

    //    DSL语句
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    //    判断
        if (StringUtils.isBlank(productName)){
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }else{
            boolQueryBuilder.must(QueryBuilders.matchQuery("productName", productName));
        }
        if(StringUtils.isNotBlank(type)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("type",type));
        }
        boolQueryBuilder.filter(QueryBuilders.termQuery("city.keyword","苏州市"));
        boolQueryBuilder.filter(QueryBuilders.termQuery("state","1"));
        request.source().query(boolQueryBuilder);
    //    排序及分页
        request.source().sort("createTime", SortOrder.DESC);
        request.source().from(currentPage - 1).size(size);
    //    高亮
        request.source().highlighter(new HighlightBuilder().field("productName").preTags("<span style='color: #8a00ff; font-size: 22px;'>").postTags("</span>"));
    //     发送请求
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

    //    响应解析
        SearchHits responseHits = response.getHits();
    //    获取总条数
        long total = responseHits.getTotalHits().value;
    //    文档数据
        SearchHit[] hits = responseHits.getHits();
        for(SearchHit hit : hits){
            String json = hit.getSourceAsString();
            FirstHeadProductDTO firstHeadProductDTO = JSONObject.parseObject(json, FirstHeadProductDTO.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(!CollectionUtils.isEmpty(highlightFields)){
                HighlightField highlightField = highlightFields.get("productName");
                if(highlightField != null){
                    String highLightProductName = highlightField.getFragments()[0].string();
                    firstHeadProductDTO.setProductName(highLightProductName);
                }
            }

            headProductDTOList.add(firstHeadProductDTO);
        }
        List<FirstHeadProductDTO> handeledHeadProductDTOList = service.firstHeadProductResponseDataHandel(headProductDTOList);
        result.put("total",total);
        result.put("body",handeledHeadProductDTOList);
        return result;
    }

    @GetMapping("/selectAllProductByPage")
    public IPage<FirstHeadProductDTO> selectAllProductByPage(Integer beginNum, Integer size,String city){
        IPage<FirstHeadProductDTO> firstHeadProductDTOpage = service.selectAllProductByPage(beginNum, size,city);
        return firstHeadProductDTOpage;
    }

    @GetMapping("/selectAllHousesByCondition")
    public Map<String,Object> selectAllHousesByCondition(Integer beginNum, Integer size,String city,String type){
        String _type = type.equals("New") ? "0" : type.equals("Sec") ? "1" : "2";
        Map<String,Object> result = new HashMap<>();
        IPage<FirstHeadProductDTO> firstHeadProductDTOpage = service.selectAllHousesByCondition(beginNum, size,city,_type);
        Integer count = service.selectAllProductCountByCityAndCondition(city, _type);

        result.put("body",firstHeadProductDTOpage);
        result.put("count",count);
        return result;
    }

    @GetMapping("/selectAllProductCountByCity")
    @CustomerAopNote
    public Map<String,Object> selectAllProductCountByCity(String city){

        Map<String,Object> result = new HashMap<>();

        Integer count = service.selectAllProductCountByCity(city);

        result.put("count",count);

        return result;
    }

    @GetMapping("/selectOneProductByID")
    @CustomerAopNote
    public Map<String,Object> selectOneProductByID(String productId){
        Map<String,Object> result = new HashMap<>();
        FirstHeadProductDTO body = service.selectOneProductByID(productId);
        result.put("body",body);
        return result;
    }

    @GetMapping("/selectAllProductIsSubscribed")
    public Map<String,Object> selectAllProductIsSubscribed(String userId,String type){

        Map<String,Object> result = new HashMap<>();
        Map<String,String> request = new HashMap<>();
        String[] tagList = new String[10];

        if(type.equals("1") || type.equals("2") ) {

            List<FirstHeadProductDTO> productDTOS = service.selectAllProductIsSubscribed(userId, type);
            if (ObjectUtils.isEmpty(productDTOS)) {
                return null;
            }
            for (FirstHeadProductDTO fto : productDTOS) {
                request.put("userId", fto.getUserID());
                UserInfo userInfo = userClient.selectUserInfoByUserId(request);
                String newTagList = tagService.selectProductTagByDataId(fto.getTagList().replace("，", ",")).toString().replace("[", "").replace("]", "").replace(" ", "");
                tagList = newTagList.split(",");
                FirstHeadProductDTO firstHeadProductDTO = service.selectOneProductByID(fto.getProductID());
                fto.setFirstPage(firstHeadProductDTO.getFirstPage());
                fto.setUserInfo(userInfo);
            }

            result.put("productDTOS", productDTOS);
            result.put("tagList", tagList);
        }else if(type.equals("0")){
            List<FirstHeadProductDTO> productDTOS = service.selectAllProductIsSubscribedNew(userId, type);
            if (ObjectUtils.isEmpty(productDTOS)) {
                return null;
            }
            for (FirstHeadProductDTO fto : productDTOS) {
                request.put("userId", fto.getUserID());
                UserInfo userInfo = userClient.selectUserInfoByUserId(request);
                String newTagList = tagService.selectProductTagByDataId(fto.getTagList().replace("，", ",")).toString().replace("[", "").replace("]", "").replace(" ", "");
                tagList = newTagList.split(",");
                FirstHeadProductDTO firstHeadProductDTO = service.selectOneProductByID(fto.getProductID());
                fto.setFirstPage(firstHeadProductDTO.getFirstPage());
                fto.setUserInfo(userInfo);
            }

            result.put("productDTOS", productDTOS);
            result.put("tagList", tagList);
        }
      return result;
    }

    @PostMapping("/dataHandel")
    public String EsAndMysqlDataHandelAfterPay(@RequestBody Map<String,Object> request) throws IOException {
        //  获取信息
        String id = request.get("id").toString();
        String state = request.get("state").toString();
        String productId = request.get("productId").toString();

        String message = "";
        //                1.更新es
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"state",state), RequestOptions.DEFAULT);
        //                2.更新mysql
        Integer updateProductState = service.updateProductState(productId, state);
        //                3.生成订单
        return message;
    }

//    添加房源
    @PostMapping("/addNewProduct")
    public Map<String,Object> addNewProduct(@RequestBody Map<String,Object> params) throws IOException {
        Map<String,Object> result = new HashMap<>();
//         1.验证入参
        String modifyRes = modifyADDNewProductParams(params);
        if(!modifyRes.equals("true")){
            result.put("body",modifyRes);
            return result;
        }
//        获取入参
        String address = params.get("address").toString();
        String style = params.get("style").toString();
        String rentType = params.get("rentType").toString();
        String price = params.get("price").toString();
        String community = params.get("community").toString();
        String roomNum = params.get("roomNum").toString();
        String dinerNum = params.get("dinerNum").toString();
        String area = params.get("area").toString();
        String towards = params.get("towards").toString();
        String img = params.get("img").toString();
        String userId = params.get("userId").toString();
//        2.封装实体类
        FirstHeadProductDTO dto = new FirstHeadProductDTO();
        dto.setID("New-ID-"+ RandomStringUtils.randomAlphanumeric(15));

        if (style.equals("1")) {
            dto.setProductID("GRUA-SEC-P-" + RandomStringUtils.randomAlphanumeric(35));
            dto.setProductName(roomNum+"室"+dinerNum+"厅"+" "+community);
            dto.setPrice(Integer.parseInt(price+"0000"));

        }else {
            dto.setProductID("GRUA-RENT-R-" + RandomStringUtils.randomAlphanumeric(35));
            String _rentType = rentType.equals("1")?"整租":"合租";
            dto.setProductName(_rentType+"·"+roomNum+"室"+dinerNum+"厅"+"·"+community);
            dto.setPrice(Integer.parseInt(price));
        }
        dto.setType(style.charAt(0));
        dto.setProvince("320000");
        dto.setFirstPage(null);
        dto.setCity(addressFormHandel("(?<=省)[^(市)]+",address)+"市");
        dto.setProvince(addressFormHandel("(.*省)",address));
        dto.setCounty(addressFormHandel("(?<=市)[^(区|县)]+",address)+" "+addressFormHandel("(区|县)(.*)",address));
        dto.setArea(area);
        dto.setDirection(towards.length()==1?towards:(towards.charAt(0)+" "+towards.charAt(1)));
        dto.setCommunity(community);
        dto.setUserID(userId);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        dto.setCreateTime(now.format(formatter));
        dto.setState('2');

        ModelMapper modelMapper = new ModelMapper();
        EsFirstHeadProductBean esFirstHeadProductBean = modelMapper.map(dto, EsFirstHeadProductBean.class);
        esFirstHeadProductBean.setCreateTime(dto.getCreateTime().replace(" ","T"));

        Integer addNewHeadProduct = service.addNewHeadProduct(dto);
////        3.同步es
////          准备Request对象
        IndexRequest request = new IndexRequest("all_product_infos").id(dto.getID());
//         准备json数据
        Pattern imagePattern = Pattern.compile("base64,(.*)");
        Matcher matcher = imagePattern.matcher(img);
        if (matcher.find()) {
            // 获取Base64编码的图像数据
            String base64Image = matcher.group(1);
            esFirstHeadProductBean.setFirstPage(base64Image);
        }
        request.source(JSON.toJSONString(esFirstHeadProductBean), XContentType.JSON);
//         发送请求
         esClient.index(request, RequestOptions.DEFAULT);
         result.put("body","success");
         result.put("productId",dto.getProductID());
         return result;
    }

//    上传图片
    @PostMapping("/insertImg")
    public String insertImg(FirstHeadProductImgeBean bean) {
        int readBytes = 0;
        if (bean.getFile().isEmpty()) {
            return "图片未上传！";
        }
        try {
            InputStream is = bean.getFile().getInputStream();
            byte[] pic = new byte[(int) bean.getFile().getSize()];
            int len = pic.length;
//            读取文件
            while (readBytes < len) {
                int read = is.read(pic, readBytes, len - readBytes);
                //判断是不是读到了数据流的末尾 ，防止出现死循环。
                if (read == -1) {
                    break;
                }
                readBytes += read;
            }
            service.ImageInsert(pic,bean.getProductId());
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success");
        return "success";
    }

//    添加房源验证入参
    private String modifyADDNewProductParams(Map<String,Object> params){
        String addressRegex = "^([^/]+?)省([^/]+?)市([^/]+?)(区|县)([^/]+)$";
        String address = params.get("address").toString();
        String style = params.get("style").toString();
        String rentType = params.get("rentType").toString();
        String price = params.get("price").toString();
        String community = params.get("community").toString();
        String roomNum = params.get("roomNum").toString();
        String dinerNum = params.get("dinerNum").toString();
        String area = params.get("area").toString();
        String towards = params.get("towards").toString();
        String img = params.get("img").toString();


        if(StringUtils.isBlank(address)){
             return "请填写房源地址";
         }else if(!address.matches(addressRegex)){
            return "请填写正确格式地址";
        }

        if(style.equals("1")&&StringUtils.isNotBlank(rentType)){
            rentType = "";
        }

        if(style.equals("2")&&StringUtils.isBlank(rentType)){
            return "请选择您的租房类型";
        }

        if (StringUtils.isBlank(price)){
            return "请填写您的房源价格";
        }

        if (StringUtils.isBlank(community)){
            return "请填写您的小区";
        }

        if (StringUtils.isBlank(roomNum)){
            return "请填写您的房间数";
        }

        if (StringUtils.isBlank(dinerNum)){
            return "请填写您的厅数";
        }

        if (StringUtils.isBlank(area)){
            return "请填写您的房产面积";
        }

        if (StringUtils.isBlank(towards)){
            return "请填写您的房产朝向";
        }

        if (StringUtils.isBlank(img)){
            return "您还未上传图片";
        }

         return "true";
    }


    private String addressFormHandel(String regex,String address){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        String result = "";

        if (matcher.find()) {
            if (regex.equals("(区|县)(.*)")){
                result = matcher.group(2);
            }else {
                result = matcher.group();
            }
        }

        return result;
    }

    @GetMapping("/selectAllConfimingProduct")
    public Map<String,Object> selectAllConfimingProduct(Integer beginNum, Integer size){
        Map<String,Object> result = new HashMap<>();
        Map<String,String> request = new HashMap<>();
        IPage<FirstHeadProductDTO> firstHeadProductDTOIPage = service.selectAllConfimingProduct(beginNum, size);
        List<FirstHeadProductDTO> records = firstHeadProductDTOIPage.getRecords();
        for (FirstHeadProductDTO fto : records){
            request.put("userId",fto.getUserID());
            UserInfo userInfo = userClient.selectUserInfoByUserId(request);
            fto.setUserInfo(userInfo);
        }
        result.put("records",records);
        result.put("total",firstHeadProductDTOIPage.getTotal());
        return result;
    }


    @PostMapping("/selectAllMyPubshingByCondition")
    public Map<String,Object> selectAllMyPubshingByCondition(@RequestBody Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
//        获取参数
        Integer currentPage =  Integer.parseInt(params.get("currentPage").toString());
        Integer pageSize =  Integer.parseInt(params.get("pageSize").toString());
        String productName =  params.get("productName").toString();
        String type =  params.get("type").toString();
        String state =  params.get("state").toString();
        String userId =  params.get("userId").toString();

         return service.selectAllMyPubshingByCondition(currentPage,pageSize,productName,type,state,userId);
    }

    @PostMapping("/againstProductPublish")
    public Integer againstProductPublish(@RequestBody Map<String,Object> params) throws IOException {
//         获取参数
        String productId = params.get("productId").toString();
        String id = params.get("id").toString();
        String againstReason = params.get("againstReason").toString();
//        修改数据库
        Integer updated = service.againstProductPublish(productId, againstReason);
//         同步es
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"state","0"), RequestOptions.DEFAULT);
        return updated;
    }

    @GetMapping("/reverseApplication")
    public Integer reverseApplication(String productId,String id) throws IOException {
//        修改数据库
        Integer updated = service.reverseApplication(productId);
//         同步es
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"state","4"), RequestOptions.DEFAULT);
        return updated;
    }

    @GetMapping("/offShellHandel")
    public Integer offShellHandel(String productId,String id) throws IOException {
//        修改数据库
        Integer updated = service.offShellHandel(productId);
//         同步es
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"state","5"), RequestOptions.DEFAULT);
        return updated;
    }

    @GetMapping("/lookUpResaon")
    public String lookUpResaon(String productId){
         return service.lookUpResaon(productId);
    }

//    审批通过
//    1.标签查询
    @GetMapping("/selectAllTags")
    public List<ProductConfig> selectAllTags(){
         return tagService.selectAllTags();
    }

//    更新表信息
    @PostMapping("/insertNewHouseInfo")
    public Integer insertNewHouseInfo(@RequestBody Map<String,Object> params) throws IOException {

        List<String> tagList = Arrays.asList(params.get("tagList").toString());
        String floor = params.get("floor").toString();
        String fitment = params.get("fitment").toString();
        String lift = params.get("lift").toString();
        String floorType = params.get("floorType").toString();
        String type = params.get("type").toString();
        String ownerShip = params.get("ownerShip").toString();
        String age = params.get("age").toString();
        String comment = params.get("comment").toString();
        String productId = params.get("productId").toString();
        String userId = params.get("userId").toString();
        String id = params.get("id").toString();

        float productValue = Float.parseFloat(params.get("productValue").toString());
        float supportValue = Float.parseFloat(params.get("supportValue").toString());
        float tradeValue = Float.parseFloat(params.get("tradeValue").toString());
        float areaValue = Float.parseFloat(params.get("areaValue").toString());
        String s1 = tagList.get(0);
        String tags = s1.replace("[","").replace("]","").replace(",","，").trim();
//        1.更新主表标签并同步es
        Integer updated = service.updateTagAndState(productId, tags);
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"state","1"), RequestOptions.DEFAULT);
        esClient.update(EsHandelUtils.HandelEsUpdateMethod("all_product_infos",id,"tagList",tags), RequestOptions.DEFAULT);

//        2.更新其他信息
        DoubelRentProductDetails details = new DoubelRentProductDetails();
        details.setProductID(productId);
        details.setIsSubscrib('0');
        details.setFloor(floor);
        details.setInsideSet("暂无数据");
        details.setFitment(fitment);
        details.setLift(lift.charAt(0));
        details.setFloorType(floorType);
        details.setType(type);
        details.setOwnerShip(ownerShip);
        details.setAge(age);
        details.setProductValue(productValue);
        details.setSupportValue(supportValue);
        details.setTradeValue(tradeValue);
        details.setAreaValue(areaValue);
        details.setComment(comment);
        details.setSubscribedUserId(null);
        details.setRate(String.valueOf(((productValue+supportValue+tradeValue+areaValue)/4)));

        Integer updateNewProductDetails = secPlusRentProductDetailService.updateNewProductDetails(details);
        return 1;
    }


//    插入图片信息
    @PostMapping("/insertImgForDetails")
    public String insertImgForDetails(ProductDetailsImgeBean bean) {
        int readBytes = 0;
        if (bean.getFile().isEmpty()) {
            return "图片未上传！";
        }
        try {
            InputStream is = bean.getFile().getInputStream();
            byte[] pic = new byte[(int) bean.getFile().getSize()];
            int len = pic.length;
    //            读取文件
            while (readBytes < len) {
                int read = is.read(pic, readBytes, len - readBytes);
                //判断是不是读到了数据流的末尾 ，防止出现死循环。
                if (read == -1) {
                    break;
                }
                readBytes += read;
            }
            secPlusRentProductDetailService.updateNewProductDetailImgs(bean.getSign(),pic,bean.getProductId());
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("success");
        return "success";
    }


}
