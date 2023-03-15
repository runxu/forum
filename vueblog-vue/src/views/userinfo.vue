<template>

    <div>
        <Header></Header>
        <div class="bg">
            <el-row type="flex" justify="center">
                <el-col :span="14">
                    <el-card>
                        <div class="daohang">
                        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                            <el-menu-item index="1" @click="touserinfo">我的主页</el-menu-item>
                            <el-menu-item index="2">我的关注</el-menu-item>
                            <el-submenu index="3">
                                <template slot="title">安全设置</template>
                                <el-menu-item index="3-1" @click="toupdatepassword">修改密码</el-menu-item>
                                <el-menu-item index="3-2" @click="toupdatephone">修改手机密保</el-menu-item>
                            </el-submenu>
                        </el-menu>
                        </div>
                        <div>
                            <!-- <el-image :src="require('E:/treehole/upload/'+user.img)" style="width: 200px; height: 200px;margin-right: 50px;"></el-image> -->
                            <el-upload style="display: inline-block;margin-right: 50px;" class="avatar-uploader" action="#" :show-file-list="false"
                                       :on-success="handleAvatarSuccess" :on-change="handleChange" :before-upload="beforeAvatarUpload">
                                <img v-if="this.user.avatar" :src="require('C:/files/images/'+this.user.avatar)" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                            <div style="display: inline-block;margin-top: 20px;">
                                <span style="font-size: xx-large;">{{this.user.username}}</span><br>

                                <span style="font-size:5px">{{this.user.email}}</span><br>
                                <el-link @click="toEditPersonalPage()" type="primary" style="font-size: medium;">编辑资料>></el-link>
                                <br><br><br><br><br>
                            </div>
                        </div>

                        <div style="width: 100%;">
                            <el-tabs>
                                <el-tab-pane label="我的帖子" name="">
                                    <Myinvitation></Myinvitation>
                                </el-tab-pane>
                                <!-- 不开发了 -->
                                <!-- <el-tab-pane label="我的文章" name="">
                                    <MyArticle></MyArticle>
                                </el-tab-pane> -->

                                <el-tab-pane label="我的评论" name="">
                                    <Mycomment></Mycomment>
                                </el-tab-pane>

                            </el-tabs>
                        </div>

                    </el-card>

                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header"
    import Myinvitation from "../components/Myinvitation"
    import Mycomment from "../components/Mycompoment"

    export default {
        name: "userinfo",
        components:{
            Header,
            Myinvitation,
            Mycomment
        },
        data() {
            return {
                user:{
                    id:this.$store.getters.getUser.id,
                    username:this.$store.getters.getUser.username,
                    email:this.$store.getters.getUser.email,
                    avatar:this.$store.getters.getUser.avatar,
                },
                imageUrl: '',
                file: '',
                fileList: [],
                activeIndex:'1'
            }
        },
        methods:{
            handleChange(file, fileList) {
                this.fileList = fileList;
                this.file = file
                //console.log(file);
                //生成本地图片的URL，直接src显示，删除或移动后URL就失效了
                this.imageUrl = URL.createObjectURL(file.raw);
                //console.log(this.imageUrl);
                var fd = new FormData();
                var face = this.file;
                fd.append("file", face.raw);
                fd.append("user", JSON.stringify(this.user));
               this.$axios.post("/updateUserImg", fd,{
                   headers: {
                       "Authorization": localStorage.getItem("token")
                   }
                   }).then(response => {

                    console.log(response)
                   this.$store.commit("SET_USERINFO",response.data.data)
                    location.reload()
                })
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);

            },
            toEditPersonalPage(){
                this.$router.push("editpersoninfo")
            },
            touserinfo(){
                this.$router.push("userinfo")
            },
            toupdatepassword(){
                this.$router.push("/updatepassword")
            },
            toupdatephone(){
                this.$router.push("/updatephone")
            }
        },
        created(){



        },
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        isplay: inline-block;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 200px;
        height: 200px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 200px;
        height: 200px;
        display: inline-block;
    }
    .daohang{
        float: right;
    }
</style>