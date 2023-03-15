<template>
    <div>

       <Header></Header>

        <div class="mblog">
            <h4 style="font-size: larger">{{blog.title}}</h4>
            <h1>作者:{{this.username}}&nbsp;&nbsp;&nbsp;&nbsp; 更新于:{{this.blog.created}}</h1>


            <el-link icon="el-icon-edit" v-if="ownBlog">
                <router-link :to="{name: 'BlogEdit',params:{blogId:blog.id}}">编辑</router-link>
            </el-link>

            <el-link class="likeimg" @click="like" >
                <img v-if="isClick==0" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan.png" href="/blog/thumbs"></img>
                <img v-if="isClick==1" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan1.png" href="/blog/thumbs"></img>{{clickNum}}
            </el-link>

            <el-divider></el-divider>
            <div class="markdown-body" v-html="blog.content"></div>

            <el-divider></el-divider>

        </div>

        <!-- 评论显示-->
        <div class="box">
            <div class="cell">回复</div>
            <div class="fuck"></div>
            <div class="cell" v-for="c in article">
                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                    <tbody>
                    <tr>
                        <td width="48" valign="top" align="center">
                            <el-avatar v-if="c.avatar" :src="require('C:/files/images/'+c.avatar)" class="avatar"></el-avatar>
                            <el-avatar v-else icon="el-icon-user-solid"></el-avatar>
                         </td>
                        <td width="10" valign="top"></td>
                        <td width="auto" valign="top" align="left">
                            <div class="fr">
                                &nbsp; &nbsp;
                                <span class="no"></span>
                            </div>
                            <div class="sep3"></div>
                            <strong>
                                <a href="#" class="dark">{{c.username}}</a>
                            </strong>&nbsp; &nbsp;
                            <span class="ago">{{c.artcreatedtime}}</span>
                            <div class="sep5"></div>
                            <div class="reply_content">{{c.artcontent}}</div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <!-- 评论-->
        <div class="box" v-show="hasLogin">
            <div class="cell">
                <div class="fr">
                    <a>
                        <strong>↑</strong> 注意用语规范
                    </a>
                </div>添加一条新回复
            </div>
            <div class="cell">
                <form>
                <textarea
                        v-model="text"
                        name="content"
                        maxlength="10000"
                        class="mll"
                        id="reply_content"
                        style="overflow: hidden; overflow-wrap: break-word; resize: none; height: 112px;"
                ></textarea>
                    <div class="sep10"></div>
                    <div class="fr">
                        <div class="sep5"></div>
                        <span class="gray">请尽量让自己的回复能够对别人有帮助</span>
                    </div>
                    <input type="hidden" value="41475" name="once" />
                    <input type="button" value="回复" class="super normal button" @click="newcomment" />
                </form>
            </div>
            <div class="inner">
                <div class="fr">
                    <a href="#">← Top</a>
                </div>&nbsp;
            </div>
        </div>


    </div>
</template>

<script>
    import Header from "../components/Header"
    import Like from "../components/Like"
    import "github-markdown-css/github-markdown.css"
    import ElButton from "element-ui/packages/button/src/button";
    import ElIcon from "element-ui/packages/icon/src/icon";
    import ElHeader from "element-ui/packages/header/src/main";
    import ElAvatar from "element-ui/packages/avatar/src/main";

    export default {
        name: "blog-detail",
        components:{
            ElAvatar,
            ElHeader,
            ElIcon,
            ElButton,
            Header,
            Like,
        },
        data() {
            return {
            blog:{
                title: "",
                content: "",
                id:"",
                thumbs:"",
                isClick:"",
                userId:"",
                created:""
            },
                article: {
                    artcontent: "",
                    artcreatedtime: "",
                    username:"",
                    avatar:""
                },
                ownBlog: false,
                clickNum:0,
                isClick:0,
                hasLogin: false,
                text: "",
                username: "",
                commentnum: 0,
        }
        },
        methods: {
            like() {
                if (this.hasLogin == false) {
                    this.$router.push("/login")
                }
                else {
                    if (this.isClick == 0) {
                        const _this = this
                        this.isClick = 1
                        this.clickNum = this.clickNum + 1
                        console.log(this.clickNum)
                        this.$axios.get('/thumbsada?num=' + _this.clickNum + '&id=' + _this.blog.id+'&isclick='+_this.isClick+'&userid='+this.$store.getters.getUser.id).then(res => {
                            const blog = res.data.data
                            _this.isClick = blog.isclick

                        })
                    } else {
                        const _this = this
                        this.isClick = 0
                        this.clickNum = this.clickNum - 1
                        this.$axios.get('/thumbs?num=' + _this.clickNum + '&id=' + _this.blog.id+'&isclick='+_this.isClick+'&userid='+this.$store.getters.getUser.id).then(res => {
                            const blog = res.data.data

                            _this.isClick = blog.isclick

                        })
                    }
                }
            },

            newcomment() {

                const _this= this

                _this.text = this.text
                _this.blog.id = this.blog.id
               this.$axios.post('/article?id='+this.$store.getters.getUser.id+'&artcommentid='+_this.blog.id+'&content='+_this.text+'&commentnum='+_this.commentnum, {
                   headers: {
                           "Authorization": localStorage.getItem("token")
                       }
                   }).then(res => {

                            this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    location.reload();
                                }
                            });

                    })
                    .catch({});
            },


        },
        created(){



            const blogId = this.$route.params.blogId

            const _this = this


                if (blogId) {
                    this.$axios.get('/blog/' + blogId).then(res => {
                        const blog = res.data.data
                        _this.blog.id = blog.id
                        _this.blog.title = blog.title
                        _this.clickNum = blog.thumbs
                        _this.commentnum = blog.commentnum
                        _this.blog.userId = blog.userId
                        _this.blog.created = blog.created

                        var MardownIt = require("markdown-it")
                        var md = new MardownIt()

                        var result = md.render(blog.content)
                        _this.blog.content = result

                        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
                        this.$axios.get('/blogEditor/'+this.blog.userId).then(res => {
                            console.log(res)
                            this.username = res.data.data
                        })
                    })
                }

            this.$axios.get('/comment?blogid='+blogId).then(res =>{
                console.log(res)
                const comment = res.data.data
                _this.article = comment
            })

            const token = _this.$store.getters.getUser.id

            if(token == undefined){
                _this.hasLogin = false;
            }else {
                _this.hasLogin = true;
            }


            if(_this.hasLogin == true) {
                if (blogId) {
                    this.$axios.get('/blog/' + blogId + '/user_id/' + this.$store.getters.getUser.id).then(res => {
                        const blog = res.data.data
                        _this.blog.id = blog.id
                        _this.blog.title = blog.title
                        _this.clickNum = blog.thumbs
                        _this.isClick = blog.isclick
                        _this.blog.userId = blog.userId
                        _this.commentnum = blog.commentnum
                        _this.blog.created = blog.created

                        var MardownIt = require("markdown-it")
                        var md = new MardownIt()

                        var result = md.render(blog.content)
                        _this.blog.content = result

                        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
                        this.$axios.get('/blogEditor/'+this.blog.userId).then(res => {
                            console.log(res)
                            this.username = res.data.data
                        })
                    })
                }
            }
        }
    }
</script>

<style>
    .mblog{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 100%;
        padding: 20px 15px;
        margin-top: 40px;
    }
    .likeimg{
        float: right;
    }
    .box {
        background-color: #fff;
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        border-bottom: 1px solid #e2e2e2;
    }
    .cell {
        padding: 10px;
        font-size: 14px;
        line-height: 120%;
        text-align: left;
        border-bottom: 1px solid #e2e2e2;
    }
    .fr {
        float: right;
        text-align: right;
    }

    textarea {
        -webkit-writing-mode: horizontal-tb !important;
        text-rendering: auto;
        letter-spacing: normal;
        word-spacing: normal;
        text-transform: none;
        text-indent: 0px;
        text-shadow: none;
        display: inline-block;
        text-align: start;
        -webkit-appearance: textarea;

        -webkit-rtl-ordering: logical;
        flex-direction: column;
        cursor: text;
        white-space: pre-wrap;
        overflow-wrap: break-word;
        margin: 0em;
        font: 400 13.3333px Arial;
        border-width: 1px;
        border-style: solid;
        border-color: rgb(169, 169, 169);
        border-image: initial;
        padding: 2px;
        width: 900px;
    }
    .inner {
        padding: 10px;
        font-size: 14px;
        line-height: 150%;
        text-align: left;
    }
    strong {
        font-weight: bold;
    }
    .sep10 {
        height: 10px;
    }

</style>