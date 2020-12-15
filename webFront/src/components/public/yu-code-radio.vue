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
    import Config from '@/config';

    // 数据字典组件
    export default {
        name: 'yu-code-radio',
        data(){
            return {
                options: [],
                val: null
            }
        },
        props: {
            code: Number,
            initValue: Number
        },
        created(){
            let code = this.$props.code;
            let initValue = this.$props.initValue;
            if(code){
                this.options = Config.get('$code')[code];
            }
            if(initValue){
                this.val = initValue;
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
