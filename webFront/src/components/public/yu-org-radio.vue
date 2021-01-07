<template>
    <el-cascader
            v-model="val"
            :options="options"
            :props="defaultProps"
            @change="afterChange"></el-cascader>
</template>

<script>
    import OrgUtil from '@/utils/OrgUtil';

    // 数据字典组件
    export default {
        name: 'yu-org-radio',
        data(){
            return {
                options: OrgUtil.getOrgTree()[0]['children'],
                val: null,
                defaultProps: {
                    value: 'orgId',
                    label: 'orgName'
                }
            }
        },
        props: {
            orgId: Number,
            initValue: Number
        },
        created(){
            let orgId = this.$props.orgId;
            console.log(orgId);
            let initValue = this.$props.initValue;
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
            },
            setInitValue(initValue){
                this.val = initValue;
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
