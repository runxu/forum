<template>
    <div>
        <Header></Header>
        <div class="bg">
            <el-row type="flex" justify="center">
                <el-col :span="10">
                    <el-card class="box-card" style="padding: 30px 10px;">
                        <el-row type="flex" justify="center">
                            <el-col :span="20">
                                <el-form label-width="80px">
                                    <el-form-item label="旧密码">
                                        <el-input prefix-icon="el-icon-lock" v-model.trim="oldpassword" placeholder="请输入密码"></el-input>
                                    </el-form-item>
                                    <el-form-item class="opt"
                                                  label="新密码"
                                                  prop="password">
                                        <el-input prefix-icon="el-icon-lock"
                                                  placeholder="请输入密码"
                                                  :type="[flag?'text':'password']"
                                                  v-model.trim="newpassword">
                                            <i slot="suffix"
                                               :class="[flag?'el-icon-minus':'el-icon-view']"
                                               style="margin-top:8px;font-size:18px;"
                                               autocomplete="auto"
                                               @click="flag=!flag" />
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item label="密保手机">
                                        <el-input v-model="phone" placeholder="请输入密保手机"></el-input>
                                    </el-form-item>
                                    <el-form-item label="验证码">
                                        <el-input  v-model="code" style="width: 65%; float: left"></el-input>
                                        <el-button type="primary" style="margin-left: 10px"  :disabled="btnDisabled" @click="sendsms">{{btnText}}</el-button>
                                        <span id="msg" style="color: red">{{msg}}</span>
                                    </el-form-item>
                                </el-form>
                                <el-button type="primary" style="margin-left: 50px;" @click="UpdateUser()">修改</el-button>
                                <el-button type="text" @click="handleCancle()">返回</el-button>
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
        name: "updatepassword",
        data() {
            return {
                oldpassword: '',
                newpassword:'',
                phone:'',
                code:'',
                msg:'',
                btnDisabled: false,
                btnText:'发送验证码',
                flag: false,
            }
        },
        methods:{
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

            UpdateUser() {

                const _this= this
                var fd = new FormData();
                fd.append("userid", this.$store.getters.getUser.id);
                fd.append("oldpassword", this.oldpassword);
                fd.append("newpassword",this.newpassword);
                fd.append("phone",this.phone);
                fd.append("code",this.code);
                _this.$axios.post("/updateUserPassword",fd, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    this.$alert('修改成功', '修改密码', {
                        confirmButtonText: '去登陆',
                        callback: action => {
                            _this.$store.commit("REMOVE_INFO")
                            _this.$router.push("/login")
                        }
                    })
                })

            },


            countDown(num) {
                //将按钮变得不可用
                this.btnDisabled = true;
                this.btnText = num + "秒可发送";
                //倒计时，方法的返回值就是计时器
                let timer = setInterval(() => {
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
                        clearInterval(timer);
                    }
                }, 1000);
            }
        },


    }


</script>

<style scoped>

</style>