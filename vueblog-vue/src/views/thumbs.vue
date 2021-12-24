<template>
    <div>

        <Header></Header>
        <div class="mblog">

            <h2>{{blog.title}}</h2>

            <el-link icon="el-icon-edit" v-if="ownBlog">
                <router-link :to="{name: 'BlogEdit',params:{blogId:blog.id}}">编辑</router-link>
            </el-link>

            <div class="likeimg" @click="like" >
                <img v-if="isClick==0" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan.png" href="/blog/thumbs"></img>
                <img v-if="isClick==1" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan1.png" href="/blog/thumbs"></img>{{clickNum}}
            </div>


            <el-divider></el-divider>
            <div class="markdown-body" v-html="blog.content"></div>

        </div>
    </div>
</template>

<script>
    import Header from "../components/Header"
    import Like from "../components/Like"
    import "github-markdown-css/github-markdown.css"

    export default {
        name: "thumbs",
        components:{
            Header,
            Like,
        },
        data() {
            return {
                blog:{
                    title: "",
                    content: "",
                    id:""
                },
                ownBlog: false,
                clickNum:0,
                isClick:''
            }
        },
        created(){
            const blogId = this.$route.params.blogId
            console.log(blogId)
            const _this = this
            if(blogId) {
                this.$axios.get('/blog/' + blogId).then(res => {
                    const blog = res.data.data
                    _this.blog.id = blog.id
                    _this.blog.title = blog.title

                    var MardownIt = require("markdown-it")
                    var md = new MardownIt()

                    var result = md.render(blog.content)
                    _this.blog.content = result

                    _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
                })
            }
        }
    }
</script>

<style scoped>
    .mblog{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 100%;
        height: 700px;
        padding: 20px 15px;
        margin-top: 30px;
    }


</style>