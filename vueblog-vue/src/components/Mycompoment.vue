<template>
    <div>
        <div v-if="article">
            <h3>{{total}}个回答</h3>
            <div v-for="(comment, index) in article">
                <el-divider class="fengexian"></el-divider>
                    <el-link @click="handleClick(comment.artCommentId)">
                    <div v-text="comment.artcontent" ></div>
                </el-link>
                    <div style="display: inline-block;float: right;">
                        <font color="grey">{{comment.artcreatedtime}}</font>
                    </div>
            </div>
            <el-divider></el-divider>
            <div class="block">
                <el-pagination class="mpage"
                               background
                               layout="prev, pager, next"
                               :current_page = "currentPage"
                               :page-size="pageSize"
                               @current-change=page
                               :total="total">
                </el-pagination>
            </div>
        </div>
        <div v-else>
            您还未发表过回答
        </div>
    </div>
</template>

<script>
    import ElLink from "element-ui/packages/link/src/main";

    export default {
        components: {ElLink},
        name: "mycompoment",
        data() {
            return {

                article: {
                    artCommentId:'',
                    artcontent: '',
                    artcreatedtime: '',
                },
                currentPage: 1,
                total: 0,
                pageSize: 4,
                keyword:'',
            }
        },methods:{
            handleClick(aid) {
                this.$router.push({name:'BlogDetail',params: {blogId:aid}})
            },

            page(currentPage){
                const _this = this

                _this.$axios.get("/mycomment?currentPage="+currentPage+"&userId="+this.$store.getters.getUser.id).then(res=>{
                    console.log(res)
                    _this.article = res.data.data.records
                    _this.currentPage = res.data.data.currentPage
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })

            }
        },
        created(){
           this.page(1)
        }
    }
</script>

<style>
    h3 {
        margin-top: 0px;
        margin-bottom: 0px;
    }

    .fengexian {
        margin-top: 20px;
        margin-bottom: 10px;
    }
</style>