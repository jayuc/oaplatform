<template>
    <el-select v-model="value" placeholder="请选择">
        <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
        </el-option>
    </el-select>
</template>

<script>

    // 数据字典组件
    export default {
        name: 'yu-code-radio',
        data(){
            return {
                options: [{
                    value: 1,
                    label: '黄金糕'
                }, {
                    value: 2,
                    label: '双皮奶'
                }],
                value: null
            }
        },
        model: {
            event: 'change',
            prop: 'value'
        },
        props: {
//            value1: Number
        },
        mounted(){

        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
