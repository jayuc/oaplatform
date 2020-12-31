<template>
    <div>
        首页正在开发
    </div>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';

    export default {
        name: 'main-main',
        methods: {

        },
        mounted(){
            RestUtil.get('oa/bill/pending').then((list) => {
                console.log(list);
            });
        }
    }
</script>

<style scoped>

</style>
