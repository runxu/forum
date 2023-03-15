<template>
  <div class="first">


      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input  v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="phone">
            <el-input  v-model="ruleForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-input  v-model="code" style="width: 65%; float: left"></el-input>
            <el-button type="primary" style="margin-left: 10px"  :disabled="btnDisabled" @click="sendsms">{{btnText}}</el-button>
            <span id="msg" style="color: red">{{msg}}</span>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">立即注册</el-button>

          </el-form-item>
        </el-form>

      </el-main>



  </div>
</template>


<script>
    import ElButton from "element-ui/packages/button/src/button";

    export default {
        components: {ElButton},
        name: "Register",
        data() {
            return {
                ruleForm: {
                    username: '',
                    password: '',
                    email: '',
                    phone:'',

                },
                code:'',
                msg:'',
                btnDisabled: false,
                btnText:'发送验证码',
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'change' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'change' }
                    ],
                    phone:[
                        {required: true,message:"请输入手机号",tigger:'blur'},
                        {pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}
                    ],

                },
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        let param = {
                            user: this.ruleForm,
                            code: this.code
                        };
                        this.$axios.post('/register', param).then(res => {

                                this.$alert('注册成功', '论坛注册', {
                                    confirmButtonText: '前往登录',
                                    callback: action => {
                                        _this.$router.push("/login")
                                    }
                                });


                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            sendsms(){
                let reg = /^1[3-9]\d{9}$/;
                if( reg.test(this.ruleForm.phone)) {
                    this.$axios.post('/sendSms?phone=' + this.ruleForm.phone).then(res => {
                        this.countDown(60);
                    })
                }else{
                    alert("请先输入手机号")
                }
            },
            //倒计时60秒
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
        }
    }
</script>

<style scoped>
.first {
  margin-top: 50px;
  text-align: center;
  float: left;
  width: 500px;
  height: 400px;
}
.button-tj{
  float: left;
  margin-left: 212px;
  margin-top: 10px;
  }
</style>