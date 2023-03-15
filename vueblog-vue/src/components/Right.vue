<template>
    <div class="right">
        <el-container>
            <el-container>
            <el-main>

              <el-card class="box-card" style="margin-top: 13px;width: 80%;margin-left: 10px;margin-bottom: 20px">
                <el-row type="flex" justify="center" style="text-align: center;">
                  <el-link href="/blog/add" type="primary">
                    <svg width="40" height="40" viewBox="0 0 40 40" class="GlobalWrite-navIcon" fill="currentColor">
                      <g fill="#0084FF" fill-rule="evenodd">
                        <circle cx="20" cy="20" r="20" opacity=".12"></circle>
                        <path d="M23.487 10.463c1.896 0 2.583.193 3.277.555a3.824 3.824 0 0 1 1.607 1.573c.371.678.569 1.35.569 3.206v8.472c0 1.855-.198 2.527-.569 3.205a3.824 3.824 0 0 1-1.607 1.573c-.694.363-1.381.556-3.277.556h-6.96c-1.895 0-2.583-.193-3.276-.556a3.824 3.824 0 0 1-1.608-1.573c-.37-.678-.568-1.35-.568-3.205v-8.472c0-1.855.197-2.528.568-3.206.37-.678.915-1.21 1.608-1.573.693-.362 1.38-.556 3.277-.556h6.959zm0 2.08h-6.96c-1.407 0-1.836.081-2.273.31a1.72 1.72 0 0 0-.735.72c-.234.427-.317.847-.317 2.224v8.472c0 1.377.083 1.796.317 2.224.172.316.412.551.735.72.437.229.866.31 2.274.31h6.959c1.407 0 1.836-.081 2.274-.31a1.72 1.72 0 0 0 .735-.72c.234-.428.317-.847.317-2.224v-8.472c0-1.377-.083-1.797-.317-2.225a1.72 1.72 0 0 0-.735-.72c-.438-.228-.867-.309-2.274-.309zm-1.991 9.778v1.873h-5.955V22.32h5.955zm2.977-3.328v1.872h-8.932v-1.872h8.932zm0-3.33v1.873h-8.932v-1.872h8.932z"
                              fill-rule="nonzero"></path>
                      </g>
                    </svg>
                    <br>
                    <span>发文章</span>
                  </el-link>
                </el-row>
              </el-card>

                <div class="tile"  v-if="guanggao">
                    <article class="tile is-child box">
                        <el-carousel indicator-position="outside">
                            <el-carousel-item v-for="item in adimg" :key="item.title">
                               <div style="position: absolute;right: 0px">
                                <b-tag
                                        attached
                                        closable
                                        aria-close-label="Close tag"
                                        @close="guanggao = false">
                                    广告
                                </b-tag >
                               </div>
                                <img :src="require('C:/files/images/'+item.img)" style="height:100%;width:100%;" @click="toadLink(item.link)">
                            </el-carousel-item>
                        </el-carousel>
                    </article>
                </div>

                <div class="tianqi" v-if="tian">
                    <div class="tqsd"><b-tag
                            attached
                            closable
                            aria-close-label="Close tag"
                            @close="tian = false">
                    </b-tag></div>
                    <div id="he-plugin-standard"></div>
                </div>

        <div class="fly-panel-pp">
            <h3 class="fly-panel-title">友情链接</h3>

            <el-link href="http://www.baidu.com/" target="_blank" style="color: blue;margin: 20px">Baidu</el-link>
            <el-link href="http://www.goole.com/" target="_blank" style="color: blue;margin: 20px">Goole</el-link>
            <el-link href="http://www.firefox.com.cn/" target="_blank" style="color: blue;margin: 20px">Firefox</el-link>
        </div>
            </el-main>
            </el-container>
        </el-container>
    </div>

</template>

<script>
    import ElMain from "element-ui/packages/main/src/main";
    import ElContainer from "element-ui/packages/container/src/main";

    export default {
        components: {
            ElContainer,
            ElMain},
        name: "right",
        data(){
            return{
                blog:{
                    title: "",
                    content: "",
                    id:"",
                    thumbs:"",
                    isClick:"",
                },
                article: {

                    artcontent: "",
                    artcreatedtime: "",
                    username:""
                },
                ownBlog: false,
                clickNum:0,
                isClick:0,
                hasLogin: false,
                text: "",
                userid: '',
                blogs: {},
                category:{},
                isCollapse: true,
                isTag3Active:true,
                guanggao:true,
                tian:true,
                adimg:{
                }
            }
        },
        methods: {
            Post(){
                this.$router.push("/blog/add")
            },
            getImgUrl(value) {
                return `https://picsum.photos/id/43${value}/1230/500`
            },
            toadd(){
               this.$router.push("/blog/add")
            },
            refresh(){
                location.reload();
            },
            tocategory(cid){
                this.$router.push({name:'Category',params: {categoryid:cid}})
            },
            touserinfo(){
                this.$router.push("/userinfo")
            },
            entercateid(cid){
                this.$router.push({name:'Category',params: {categoryid:cid}})
                location.reload();

            },
            getImgUrl(value) {
                return `https://picsum.photos/id/43${value}/1230/500`
            },
            toadLink(link){
                window.location.href = (link)
            }
        },
        created() {

                window.WIDGET = {
                    "CONFIG": {
                        "layout": "2",
                        "width": 150,
                        "height": 240,
                        "background": "1",
                        "dataColor": "FFFFFF",
                        "borderRadius": "10",
                        "key": "408cc020fc644e039f6f0d4eedd5c5af"  //这里换成自己的key！
                    }
                }
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.src = 'https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0';
                document.getElementsByTagName('head')[0].appendChild(script);

            const _this = this
            _this.$axios.get("/lunbo").then(res=>{
                console.log(res)
                _this.category = res.data.data
            })

            _this.$axios.get("/adimg").then(res=>{
                console.log(res)
                _this.adimg = res.data.data
            })


            const token = _this.$store.getters.getUser.id
            if(token>0){
                _this.hasLogin = true;
            }else {
                _this.hasLogin = false;
            }

            }



    }
</script>

<style scoped>

    .el-carousel__item h3 {
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }


        .fly-panel-pp{
           position: absolute;
            top: 700px;
            margin-left: 30px;
           border-bottom: 1px dotted #E9E9E9; color: #333; border-radius: 2px 2px 0 0; font-size: 14px;
        }
        .right{

        }

        body > .el-container {

        }


        .el-container:nth-child(5) .el-aside,
        .el-container:nth-child(6) .el-aside {
            line-height: 260px;
        }

        .el-container:nth-child(7) .el-aside {
            line-height: 320px;
        }

        .is-active .al img {
            filter: grayscale(0%);
        }
        .al img {
            filter: grayscale(100%);
        }

    .tianqi{
        position: fixed;
        left: 0px;
        bottom: 50px;
    }
    .tile{
        margin-left: 10px;
    }
.tqsd{
    float: left;
    position: fixed;
    left: 127px;
    bottom: 265px;
}



</style>