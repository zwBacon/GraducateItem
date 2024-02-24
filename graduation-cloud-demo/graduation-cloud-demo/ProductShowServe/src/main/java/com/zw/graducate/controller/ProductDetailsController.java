package com.zw.graducate.controller;

import com.zw.graducate.clients.UserClient;
import com.zw.graducate.entity.DoubelRentProductDetails;
import com.zw.graducate.entity.NewProductDetails;
import com.zw.graducate.entity.UserInfo;
import com.zw.graducate.service.NewProductDetailService;
import com.zw.graducate.service.SecPlusRentProductDetailService;
import com.zw.graducate.util.BeanToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/22 11:00
 */
@RestController
@RequestMapping("/productDetail")
@CrossOrigin(origins = {"*", "null"})
public class ProductDetailsController {


    @Autowired
    private SecPlusRentProductDetailService rentProductDetailService;

    @Autowired
    private NewProductDetailService newProductDetailService;

    @Autowired
    private UserClient userClient;

       @PostMapping("/selectOneProductDetailById")
       public Map<String,Object> selectOneProductDetailById(@RequestBody Map<String,String> requestBody){

           Map<String,Object> result = new HashMap<>();
           Map<String,String> request = new HashMap<>();
           List<byte[]> imgList = new ArrayList<>();

           String type = requestBody.get("type");
           String productId = requestBody.get("productId");
           String userId = requestBody.get("userId");

           request.put("userId",userId);
           UserInfo userInfo = userClient.selectUserInfoByUserId(request);

           if(type.equals("1")||type.equals("2")){
               DoubelRentProductDetails doubelRentProductDetails = rentProductDetailService.selOneDoubelRentProductDetailByProductId(productId);
//               图片封装集合
               imgList.add(doubelRentProductDetails.getDetailImg1());
               imgList.add(doubelRentProductDetails.getDetailImg2());
               imgList.add(doubelRentProductDetails.getDetailImg3());
               imgList.add(doubelRentProductDetails.getDetailImg4());
               result.put("imgList",imgList);

               DoubelRentProductDetails body = DoubelRentProductDetails.builder().age(doubelRentProductDetails.getAge()).productID(doubelRentProductDetails.getProductID())
                                          .fitment(doubelRentProductDetails.getFitment()).floor(doubelRentProductDetails.getFloor())
                                          .floorType(doubelRentProductDetails.getFloorType()).insideSet(doubelRentProductDetails.getInsideSet())
                                          .lift(doubelRentProductDetails.getLift()).ownerShip(doubelRentProductDetails.getOwnerShip())
                                          .isSubscrib(doubelRentProductDetails.getIsSubscrib()).type(doubelRentProductDetails.getType()).userInfo(userInfo)
                                          .productValue(doubelRentProductDetails.getProductValue()).areaValue(doubelRentProductDetails.getAreaValue()).rate(doubelRentProductDetails.getRate())
                                          .supportValue(doubelRentProductDetails.getSupportValue()).tradeValue(doubelRentProductDetails.getTradeValue()).comment(doubelRentProductDetails.getComment()).build();
               result.put("body",body);
           }else {
               NewProductDetails newDetail = newProductDetailService.selOneNewProductDetailByProductId(productId);

               imgList.add(newDetail.getDetailImg1());
               imgList.add(newDetail.getDetailImg2());
               imgList.add(newDetail.getDetailImg3());
               imgList.add(newDetail.getDetailImg4());

               result.put("imgList",imgList);

               NewProductDetails body = NewProductDetails.builder().address(newDetail.getAddress()).area(newDetail.getArea()).electricSupply(newDetail.getElectricSupply())
                                          .equity(newDetail.getEquity()).floorArea(newDetail.getFloorArea()).floorSpace(newDetail.getFloorSpace()).greenRate(newDetail.getGreenRate()).heatSupply(newDetail.getHeatSupply())
                                           .isSubscrib(newDetail.getIsSubscrib()).openTime(newDetail.getOpenTime()).parkAvailability(newDetail.getParkAvailability()).parkSpaceRatio(newDetail.getParkSpaceRatio())
                                          .planHouseHold(newDetail.getPlanHouseHold()).productID(newDetail.getProductID()).propertyFee(newDetail.getPropertyFee()).renovation(newDetail.getRenovation()).strataCompany(newDetail.getStrataCompany())
                                          .typeOfBuild(newDetail.getTypeOfBuild()).usage(newDetail.getUsage()).userInfo(userInfo).waterSupply(newDetail.getWaterSupply()).productValue(newDetail.getProductValue())
                                          .areaValue(newDetail.getAreaValue()).rate(newDetail.getRate()).comment(newDetail.getComment()).tradeValue(newDetail.getTradeValue()).supportValue(newDetail.getSupportValue()).build();
               result.put("body",body);
           }


          return result;
       }

       @GetMapping("/updateSubscribedById")
       public Map<String,Object> updateSubscribeById(String type,Integer opertion,String productId,String userId){
           Map<String,Object> result = new HashMap<>();
           int body = 0;
           if(type.equals("1")||type.equals("2")){
               body = rentProductDetailService.updateSubscribeById(productId,opertion,userId);
           }else if(type.equals("0")){
               body = newProductDetailService.updateSubscribeById(productId,opertion,userId);
           }
           result.put("body",body);
           return result;
       }

}
