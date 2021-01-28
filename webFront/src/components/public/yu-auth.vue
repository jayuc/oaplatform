<template>
    <span v-if="show" :style="theStyle">
        <slot></slot>
    </span>
</template>

<script>

    import PermissionUtil from '@/utils/PermissionUtil';

    export default {
        name: 'auth',
        data(){
            return {
                show: false,
                theStyle: ''
            }
        },
        props: {
            code: String,
            name: String
        },
        methods: {

        },
        mounted(){
            let code = this.$props.code;
            let name = this.$props.name;
            let style = this.$props.style;
            let hasPermission = PermissionUtil.hasAuth(code);
            if(hasPermission){
                this.show = true;
            }
            if(name === 'rowButton'){
                this.theStyle = 'padding: 0px 4px;';
            }
            if(style){
                this.theStyle = style;
            }
        }
    }
</script>

<style scoped>

</style>
