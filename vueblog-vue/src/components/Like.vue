<template>
    <div>

            <div class="likeimg" @click="like" >
                <img v-if="isClick==0" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan.png" href="/blog/thumbs"></img>
                <img v-if="isClick==1" class="likeimage" style="color: #30D4F5;width: 20px;height: 20px" src="./../assets/dianzan1.png" href="/blog/thumbs"></img>{{clickNum}}

            </div>
    </div>
</template>

<script>
    export default{
        name: "Like.vue",

        data() {
            return {
                clickNum:0,
                isClick:''
            }
        },
        methods:{
           like(){
               this.$router.push("/thumbs")
           }
        },
        created(){


        }
    }

</script>

<style>

</style>