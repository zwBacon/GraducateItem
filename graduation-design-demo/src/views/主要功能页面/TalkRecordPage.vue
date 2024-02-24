<template>
  <HeadLineViewForMain/>

<!--  好友列表-->
  <el-card class="background">
    <div class="chat-container">
      <div class="friend-list" style="background-color: #FAFCFF">
        <el-input type="text" v-model="searchText" placeholder="搜索好友"/><el-icon id="searchBtn" @click="selConditionChatedInfo"><Search /></el-icon>
        <ul>
          <el-collapse @change="handleChange">
            <li v-for="(friend,index) in FriendInfoList" :key=index @click="getHistoryRecord(friend.userID,friend.userName)">
              <img :src="this.stringplus + friend.headimg"  alt="friend.name">
              {{ friend.userName}}
            </li>
          </el-collapse>
        </ul>

      </div>

<!--      聊天窗口-->
      <div class="chat-window" style="background-color: #E6E8EB">
        <div class="header" style="background-color:  #9fa2a4" v-show="this.toFriendUserId !== 'message'">
          <img :src="currentFriendimageSrc" alt="currentFriend.name">{{ currentFriend}}
          <el-icon :size="21" class="button" @click="closeWindow"><CircleCloseFilled /></el-icon>
        </div>
        <ul class="messages" ref="messages" v-show="this.toFriendUserId !== 'message'">
          <li v-for="(message,index) in messages" :key=index :class="{ 'user-message': message.userId === currentUserId}">
            <img :src="currentFriendimageSrc" alt="message.sender" v-if="message.message !== ''&& message.toUserId !== this.toFriendUserId">
            <img :src="myImageSrc" alt="message.sender" v-if="message.message !== '' && message.userId === this.currentUserId">
            <div class="message-content" v-if="message.message !== ''" :class="{ 'messageBackColor': message.userId === this.currentUserId,'messageBackColor2': message.userId !== this.currentUserId}">{{ message.message }}</div>
          </li>
        </ul>
        <div class="input-box" v-show="this.toFriendUserId !== 'message'">
          <el-input type="text" v-model="sendMessageForm.message"  v-show="this.toFriendUserId !== 'message'" @keyup.enter="sendMessage"/>
          <el-button style="background-color: #a8bd81;color: whitesmoke" round @click="sendMessage" v-show="this.toFriendUserId !== 'message'">send</el-button>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
let ws = new WebSocket("ws://localhost:2000/chat");

ws.onopen = function (){

};
ws.onmessage = function (evt){
    console.log("已连")
    console.log(evt)
    console.log(sessionStorage)
};

ws.onclose = function (){

};
import HeadLineViewForMain from "@/components/HeadLineViewForMain.vue";
import axios from "axios";

export default {
  name: "TalkRecordPage",
  components: {
    HeadLineViewForMain
  },
  mounted() {
    this.selectFriendInfoDetail()
    this.toFriendUserId = this.$route.params.toUserId
    this.selectCurrentUserTitle()
    if (this.toFriendUserId !== "message"){
      this.getHistoryRecord(this.toFriendUserId);
    }
  },
  data(){
    return{
      // userId
      currentUserId:"",
      currentUserName:"",
      toFriendUserId:"",
      toFriendUserName:"",
      stringplus: "data:image/png;base64,",
      // 联系人信息集合
      FriendInfoList:[],
      searchText:"",
      // 消息集合
      messages:[],
      // 图片资源
      currentFriendimageSrc:'',
      myImageSrc:'',
    //   发送消息
      sendMessageForm:{
        message:"",
        name:"",
        toName:"",
        userId:"",
        toUserId:""
      },
      dataMessage:""
    }
  },
  methods:{
  //    查询好友列表信息
    selectFriendInfoDetail(){
      this.currentUserId = this.$route.params.userId
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/userTalkController/selAllChatedInfo?userId="+this.currentUserId,
      }).then(function (resp) {
        _this.FriendInfoList = resp.data["body"]
      })
    },
  //   查询当前用户头像
    selectCurrentUserTitle(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserIdNew?userId="+this.currentUserId,
      }).then(function (resp) {
        _this.myImageSrc = _this.stringplus + resp.data["headImg"]
        _this.currentUserName = resp.data["userName"]
      })
    },
    selectFriendUserTitle(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/baseUserController/selectUserInfoByUserIdNew?userId="+this.toFriendUserId,
      }).then(function (resp) {
        _this.currentFriendimageSrc = _this.stringplus + resp.data["headImg"]
      })
    },
  //   点击好友
    getHistoryRecord(userID,userName){
      let _this = this;
      this.toFriendUserId = userID;
      this.toFriendUserName = userName;
      this.selectFriendUserTitle();
      axios({
        method: "get",
        url: "http://localhost:2000/userTalkController/selAllRecordOfOne?userId="+this.currentUserId+"&toUserId="+userID,
      }).then(function (resp) {
        _this.messages = resp.data
        for (let i = 0; i < _this.messages.length; i++) {
          if(_this.messages[i]["userId"]===_this.currentUserId){
            _this.sendMessageForm.name = _this.messages[i]["name"]
            _this.sendMessageForm.toName = _this.messages[i]["toName"]
            break;
          }
        }
      })
    },
    // 发送消息
    sendMessage(){
      this.sendMessageForm.toUserId = this.toFriendUserId;
      this.sendMessageForm.userId = this.currentUserId;
      axios({
        method: "post",
        url: "http://localhost:2000/userTalkController/addNewChatInfo",
        data: this.sendMessageForm
      }).then(function () {
      })

      if (this.sendMessageForm.message) {
        this.dataMessage = this.sendMessageForm.message
        this.messages.push({
          message: this.sendMessageForm.message,
          userId: this.currentUserId,
          toUserId: this.toFriendUserId,
        });
        this.sendMessageForm.message = '';
        this.$refs.messages.scrollTop = this.$refs.messages.scrollHeight;
      }
      var json = {"name":this.currentUserName,"toName:":this.toFriendUserName,"message":this.sendMessageForm.message}
      ws.send(JSON.stringify(json));
    },
    closeWindow(){
      this.toFriendUserId = "message"
    },
  //   查询联系人
    selConditionChatedInfo(){
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:2000/userTalkController/selConditionChatedInfo?userId="+this.currentUserId+"&inputUserName="+this.searchText,
      }).then(function (resp) {
        _this.FriendInfoList = resp.data["body"]
      })
    }
  }
}
</script>


<style scoped>
.chat-container {
  display: flex;
  height: 680px;
  border: 1px solid #ccc;
}

.friend-list {
  flex: 1;
  padding: 10px;
  border-right: 1px solid #ccc;
}
.messageBackColor{
  background-color: #a7d997;
}
.messageBackColor2{
  background-color: white;
}
.friend-list img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.friend-list input {
  border: 1px solid #ccc;
  padding: 5px 10px;
  width: 90%;
}

.friend-list ul {
  margin-top: 10px;
  list-style: none;
  padding: 0;
}

.friend-list li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.friend-list li:hover {
  background-color: #f5f5f5;
}

.chat-window {
  flex: 3;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.header img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.messages {
  flex: 1;
  overflow: auto;
  padding: 10px;
  margin: 0;
  list-style: none;
}

.user-message {
  flex-direction: row-reverse;
}

.messages li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.messages li img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.message-content {
  padding: 10px;
  border-radius: 5px;
}

.input-box {
  display: flex;
  align-items: center;
  padding: 10px;
}

.input-box input {
  flex: 1;
  border: 1px solid #ccc;
  padding: 5px 10px;
  margin-right: 10px;
}


.input-box button:hover {
  background-color: #fc7d00;
}

/*
  Elements plus
*/
.button{
  position: fixed;
  right: 70px;
}
.button:hover{
  color: #f39417;
}
#searchBtn{
  position: fixed;
  left: 305px;
  top: 38px;
}
#searchBtn:hover{
  color: #1743f3;
}
.background{
  position: absolute;
  left:130px;
  width: 1300px;
  top:40px;
  transform: scale(0.85);
}
</style>