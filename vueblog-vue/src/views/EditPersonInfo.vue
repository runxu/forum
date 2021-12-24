<template>
    <div>
        <Header></Header>
        <div class="bg">
            <el-row type="flex" justify="center">
                <el-col :span="10">
                    <el-card class="box-card" style="padding: 30px 10px;">
                        <el-row type="flex" justify="center">
                            <el-col :span="20">
                                <el-form label-width="80px" :model="user">
                                    <el-form-item label="昵称">
                                        <el-input v-model="user.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="生日">
                                        <el-date-picker
                                                v-model="user.birthday"
                                                type="date"
                                                placeholder="选择日期">
                                        </el-date-picker>
                                    </el-form-item>
                                    <el-form-item label="所在城市">
                                        <!-- <el-input v-model="user.area" ></el-input> -->
                                        <el-select v-model="user.area">
                                            <el-option value="广东"></el-option>
                                            <el-option value="北京"></el-option>
                                            <el-option value="上海"></el-option>
                                            <el-option value="天津"></el-option>
                                            <el-option value="重庆"></el-option>
                                            <el-option value="内蒙古"></el-option>
                                            <el-option value="广西"></el-option>
                                            <el-option value="西藏"></el-option>
                                            <el-option value="宁夏"></el-option>
                                            <el-option value="新疆"></el-option>
                                            <el-option value="香港特别行政区"></el-option>
                                            <el-option value="澳门特别行政区"></el-option>
                                            <el-option value="河北"></el-option>
                                            <el-option value="山西"></el-option>
                                            <el-option value="辽宁"></el-option>
                                            <el-option value="吉林"></el-option>
                                            <el-option value="黑龙江"></el-option>
                                            <el-option value="江苏"></el-option>
                                            <el-option value="浙江"></el-option>
                                            <el-option value="安徽"></el-option>
                                            <el-option value="福建"></el-option>
                                            <el-option value="江西"></el-option>
                                            <el-option value="山东"></el-option>
                                            <el-option value="河南"></el-option>
                                            <el-option value="湖北"></el-option>
                                            <el-option value="湖南"></el-option>
                                            <el-option value="海南"></el-option>
                                            <el-option value="四川"></el-option>
                                            <el-option value="贵州"></el-option>
                                            <el-option value="云南"></el-option>
                                            <el-option value="陕西"></el-option>
                                            <el-option value="甘肃"></el-option>
                                            <el-option value="青海"></el-option>
                                            <el-option value="台湾"></el-option>
                                        </el-select>
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
        name: "edit-person-info",
        components:{
            Header
        },
        data() {
            return {
                user: {
                    username: this.$store.getters.getUser.username,
                    birthday:this.$store.getters.getUser.birthday,
                    area: this.$store.getters.getUser.area,
                },
                oldUsername: ''
            }
        },
        methods:{
            handleCancle() {
                window.history.back(-1)
            },
            UpdateUser() {

                if (this.user.username.length == 0) {
                    this.$message.error("昵称不能为空")
                    return
                } else if (this.user.username.length > 10) {
                    this.$message.error("昵称长度不能超过10个字符")
                    return
                }

                const _this= this
                var fd = new FormData();
                fd.append("userid", this.$store.getters.getUser.id);
                fd.append("username", this.user.username);
                fd.append("birthday",this.user.birthday);
                fd.append("area",this.user.area);
                _this.$axios.post("/updateUser",fd, {
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    this.$alert('修改成功', '个人资料', {
                        confirmButtonText: '确定',
                        callback: action => {

                            console.log(res)
                            const userInfo = res.data.data
                            this.$store.commit("SET_USERINFO", userInfo)
                            _this.$router.push("/userinfo")
                        }
                    })
                })

            }
        },
        created(){

        }
    }
</script>

<style>
    .bg {
        background: url(../assets/images/bg.jpg) repeat fixed center;
        width: 100%;
        height: 100%;
        position: fixed;
        left: 0px;
        background-size: 100%;
        padding: 10px;
    }
</style>