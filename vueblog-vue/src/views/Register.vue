<template>
    <div>
        <Header></Header>
                <FirstStep />
                <img src="./../assets/401.gif" style="margin-left: 50px" alt />
    </div>
</template>


<script>
    import Header from "../components/Header"
    import FirstStep from "./FirstStep";

    export default {
        components: {
            Header,
            FirstStep
        },

    };
</script>


<style scoped>


</style>