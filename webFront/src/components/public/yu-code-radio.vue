<template>
    <el-select v-model="val" placeholder="请选择" @change="afterChange">
        <el-option
                v-for="item in options"
                :key="item.codeNo"
                :label="item.name"
                :value="item.codeNo">
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
                    codeNo: 1,
                    name: '黄金糕'
                }, {
                    codeNo: 2,
                    name: '双皮奶'
                }],
                val: null
            }
        },
        props: {
            code: Number
        },
        created(){
            let code = this.$props.code;
            if(code){
                this.val = code;
            }
        },
        methods: {
            afterChange(key){
                this.$emit('change', key);
            },
            reset(){  // 复位
                this.val = null;
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
