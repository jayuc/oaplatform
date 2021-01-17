<template>
    <div class="container">
        <div class="tab_1">
            <header>待办动态</header>
            <div class="panel_list">
                <p v-for="(item,index) in applyArr" :key="index" @click="selectRow((menuObj[item.billType] || {}).path)">
                    <span class="row_name">【{{item.typeName}}】{{item.title}}</span>
                    <span class="row_type">{{item.applyName}}</span>
                    <span class="row_time">{{item.applyTime}}</span>
                </p>
                <div class="none" v-if="applyArr.length === 0">暂无数据</div>
            </div>
        </div>
        <div class="tab_2">
            <header>快捷菜单</header>
            <div class="panel_menu">
                <div v-for="(item,index) in menuObj" :key="index" @click="selectRow(item.path)">
                    {{item.name}}
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import {Loading} from 'element-ui';

    export default {
        name: 'main-main',
        data() {
            return {
                menuObj: {
                    "1": {name: '年休假申请', path: 'leave'},
                    "2": {name: '出差申请', path: 'errand'},
                    "3": {name: '经济业务支出事前申请', path: 'cost'},
                    "4": {name: '培训申请', path: 'train'},
                    "5": {name: '机关会议室申请', path: 'meeting'},
                    "6": {name: '出差交通工具调整申请', path: 'travelToolSet'},
                    "8": {name: '调阅会计档案申请', path: 'lookDoc'},
                    "10": {name: '介绍信开具申请', path: 'introduce'},
                    "11": {name: '证件（复印件）使用申请', path: 'certificate'}
                },
                applyArr: []
            }
        },
        mounted() {
            this.loadingInstance = Loading.service({
                target: '.panel_list',
                background: 'rgba(238, 241, 246, 0.6)',
                text: '正在加载'
            });
            RestUtil.get('oa/bill/pending').then((list) => {
                this.loadingInstance.close();
                this.applyArr = list;
            });
        },
        methods: {
            selectRow(path) {
                if (path) {
                    this.$router.push('/main/' + path);
                    this.$emit("openMenu", ['1'], '/main/' + path );
                }
            }
        }
    }
</script>

<style scoped>
    .container {
        width: 100%;
        height: 100%;
    }

    .container .panel_list {
        width: 100%;
        height: calc(100% - 50px);
        padding: 5px 50px;
        box-sizing: border-box;
        overflow-x: hidden;
        font-size: 14px;
        color: #fff;
    }

    .container .none {
        color: red;
        font-weight: bold;
        padding-top: 35px;
    }

    .container .panel_menu {
        max-height: calc(100% - 40px);
        padding: 15px;
        box-sizing: border-box;
        display: flex;
        justify-content: center;
        align-items: flex-start;
        flex-flow: wrap;
        overflow-x: hidden;
    }

    .container .panel_menu > div {
        width: 180px;
        height: 55px;
        line-height: 55px;
        font-size: 16px;
        font-weight: bold;
        margin: 5px 10px;
        position: relative;
        cursor: pointer;
        transition: transform 0.5s;
        border-radius: 5px;
        background: linear-gradient(to top, rgb(84, 51, 255), rgb(32, 189, 255), rgb(165, 254, 203));
    }

    .container .panel_menu > div:hover {
        box-shadow: 2px 2px 4px 1px #000000;
        transform: scale(1.05);
    }

    .container ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .container p {
        padding: 6px 10px;
        margin: 6px 0 0;
        box-sizing: border-box;
        cursor: pointer;
        display: inline-flex;
        width: 100%;
        background: linear-gradient(to right, rgb(8, 80, 120), rgb(133, 216, 206));
        box-shadow: 1px 1px 3px 0 #000;
        border-radius: 7px;
    }

    .container p:hover {
        background: #388499;
    }

    .container p > span {
        display: inline-block;
    }

    .container p .row_name {
        width: 50%;
        text-align: left;
    }

    .container p .row_type {
        width: 30%;
    }

    .container p .row_time {
        width: 20%;
    }

    .container header {
        text-align: center;
        position: relative;
        background-color: #409eff;
        width: 50%;
        height: 40px;
        line-height: 40px;
        color: #303133;
        font-size: 15px;
        margin: 0 auto;
        font-weight: bold;
    }

    .container header:before {
        content: " ";
        width: 20%;
        position: absolute;
        background: linear-gradient(to left, #409eff 0%, transparent 100%);
        height: 100%;
        top: 0;
        left: -20%;
    }

    .container header:after {
        content: " ";
        width: 20%;
        position: absolute;
        background: linear-gradient(to right, #409eff 0%, transparent 100%);
        height: 100%;
        top: 0;
        right: -20%;
    }

    .container .tab_1 {
        height: 40%;
    }

    .container .tab_2 {
        height: 60%;
    }
</style>
