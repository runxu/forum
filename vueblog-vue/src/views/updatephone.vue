<template>
    <div>
    <Header></Header>
        <div class="bg">
            <el-row type="flex" justify="center">
                <el-col :span="10">
                    <el-card class="box-card" style="padding: 30px 10px;">
                        <el-row type="flex" justify="center">
                            <el-col :span="20">
                                <div v-if="phonesucc">
                                <el-form label-width="80px" v-if="phonesucc">
                                    <el-form-item label="密保手机">
                                        <el-input v-model="phone" placeholder="请输入以前的密保手机" style="width: 65%"></el-input>
                                    </el-form-item>
                                    <el-form-item label="验证码">
                                        <el-input  v-model="code" style="width: 44%; float: left"></el-input>
                                        <el-button type="primary" style="margin-left: 10px"  :disabled="btnDisabled" @click="sendsms">{{btnText}}</el-button>
                                        <span  style="color: red">{{msg}}</span>
                                    </el-form-item>
                                </el-form>
                                    <el-button type="primary" style="margin-left: 50px;" @click="validphone()">提交</el-button>
                                    <el-button type="text" @click="handleCancle()">返回</el-button>
                                </div>
                                <div v-if="!phonesucc">
                                <el-form label-width="90px" >
                                    <el-form-item label="新密保手机">
                                        <el-input v-model="newphone" placeholder="请输入新的密保手机" style="width: 65%"></el-input>
                                    </el-form-item>
                                    <el-form-item label="验证码">
                                        <el-input  v-model="newcode" style="width: 44%; float: left"></el-input>
                                        <el-button type="primary" style="margin-left: 10px"  :disabled="btnDisabled" @click="updatephonesms">{{btnText}}</el-button>
                                        <span  style="color: red">{{msg}}</span>
                                    </el-form-item>
                                </el-form>
                                <el-button type="primary" style="margin-left: 50px;" @click="updatephone">提交</el-button>
                                <el-button type="text" @click="handleCancle()">返回</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header"
    export default {
        components:{
            Header
        },
        name: "updatephone",
        data() {
            return {
                phone:'',
                code:'',
                newphone:'',
                newcode:'',
                msg:'',
                btnDisabled: false,
                btnText:'发送验证码',
                phonesucc:true,
                timer:''
            }
        },
        methods: {
            handleCancle() {
                window.history.back(-1)
            },
            sendsms(){
                let reg = /^1[3-9]\d{9}$/;
                if( reg.test(this.phone)) {
                    var fd = new FormData();
                    fd.append("userid",this.$store.getters.getUser.id)
                    fd.append("phone",this.phone)
                    this.$axios.post("/sendPasswordSms",fd, {
                        headers: {
                            "Authorization": localStorage.getItem("token")
                        }
                    }).then(res => {
                        this.countDown(60);
                    })
                }else{
                    alert("请先输入手机号")
                }
            },


            updatephonesms(){
                let reg = /^1[3-9]\d{9}$/;
                if( reg.test(this.newphone)) {
                    var fd = new FormData();
                    fd.append("userid",this.$store.getters.getUser.id)
                    fd.append("phone",this.newphone)
                    this.$axios.post("/updatephonesms",fd, {
                        headers: {
                            "Authorization": localStorage.getItem("token")
                        }
                    }).then(res => {
                        this.countDown(60);
                    })
                }else{
                    alert("请先输入手机号")
                }
            },

            validphone(){

                var fd = new FormData();
                fd.append("phone",this.phone);
                fd.append("code",this.code);
                this.$axios.post("/validphone",fd, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    this.phonesucc = false
                    clearInterval(this.timer)
                    this.btnDisabled = false;
                    this.btnText = "发送手机验证码";
                })
            },


           updatephone(){

                var fd = new FormData();
                fd.append("userid", this.$store.getters.getUser.id);
                fd.append("phone",this.newphone);
                fd.append("code",this.newcode);
                this.$axios.post("/updatephone",fd, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    this.$alert('修改成功', '修改密保手机', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.$router.push("/userinfo")
                        }
                    })
                })
            },



            countDown(num) {
                //将按钮变得不可用

                this.btnDisabled = true;
                this.btnText = num + "秒可发送";
                //倒计时，方法的返回值就是计时器
                this.timer = setInterval(() => {
                    //倒计时减1
                    num--;
                    //修改按钮上文字
                    this.btnText = num + "秒可发送";
                    //再判断num是否为0
                    if (num == 0) {
                        //还原成原来的样子
                        this.btnDisabled = false;
                        this.btnText = "发送手机验证码";
                        //清除计时器
                        clearInterval(this.timer);
                    }
                }, 1000);
                }
            }


    }
</script>

<style scoped>

</style>