<template>
    <div>
    <Header></Header>

    <div class="first">
        <el-main>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即登录</el-button>

                </el-form-item>
            </el-form>

        </el-main>

    </div>
        <img src=https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646 class="img" alt />

    </div>
</template>





<script>
    import  "../assets/js/yz.js"
    import Header from "../components/Header"
    export default {
        name: "Login",
        components: {
            Header,
        },
        data() {
            return {
                ruleForm: {
                    username: 'BatMan',
                    password: '6658113'
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请选择密码', trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {

                        var _this = this
                        let appid = '2067800229'; // 腾讯云控制台中对应这个项目的 appid
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //生成一个滑块验证码对象
                        let captcha = new TencentCaptcha(appid, function (rep) {
                            // 用户滑动结束或者关闭弹窗，腾讯返回的内容
                            console.log(rep)
                            if (rep.ret === 0) {
                                _this.$axios.post('/login', _this.ruleForm).then(res => {
                                    console.log(res.data)
                                    const jwt = res.headers['authorization']
                                    console.log("后台传来的" + jwt)
                                    const userInfo = res.data.data

                                    // 把数据共享出去
                                    _this.$store.commit("SET_TOKEN", jwt)
                                    _this.$store.commit("SET_USERINFO", userInfo)
                                    // 获取
                                    console.log(_this.$store.getters.getUser)
                                    _this.$router.push("/sheet")
                                })

                            } else {
                                // 提示用户完成验证
                            }
                        });
                        // 滑块显示
                        captcha.show();
                    }
                })
            },

            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    body > .el-container {
        margin-bottom: 40px;
    }
    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }
    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
    .demo-ruleForm {
        max-width: 500px;
        margin: 0 auto;
    }
    .img{
        width: 400px;
        height: 400px;
    }
    .first {
        margin-top: 120px;
        text-align: center;
        float: left;
        width: 500px;
        height: 400px;
    }
</style>