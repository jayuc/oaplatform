<template>
    <el-cascader
            v-model="val"
            :options="options"
            :props="defaultProps"
            :title="orgTitle"
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
                orgTitle: '',
                defaultProps: {
                    value: 'orgId',
                    label: 'shortOrgName',
                    checkStrictly: true
                }
            }
        },
        props: {
            initValue: Number
        },
        created(){
            let initValue = this.$props.initValue;
            if(initValue){
                this.val = initValue;
                this.generateTitleByValue(initValue);
            }
        },
        methods: {
            afterChange(keyArr){
                let orgId = -1;
                if(keyArr instanceof Array && keyArr.length > 0){
                    orgId = keyArr[keyArr.length - 1];
                }
                let org = OrgUtil.getOrgById(orgId);
                if(org){
                    this.orgTitle = org.orgName;
                    this.$emit('change', orgId, org['orgCode'], org);
                }
            },
            reset(){  // 复位
                this.val = null;
            },
            setInitValue(initValue){
                this.val = initValue;
                this.generateTitleByValue(initValue);
            },
            generateTitleByValue(initValue){
                let org = OrgUtil.getOrgById(initValue);
                if(org){
                    this.orgTitle = org.orgName;
                }
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
