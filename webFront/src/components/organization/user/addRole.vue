<template>
    <el-dialog
        width="600px"
        title="选择角色权限"
        :visible.sync="visible"
        class="main-table-dialog"
        append-to-body>
        <el-table
            :data="tableData"
            border
            ref="roleTable"
            height="300px"
            rowKey="roleId"
            @selection-change="selectRow"
            style="width: 100%">
            <el-table-column
                type="selection"
                align="center"
                width="50">
            </el-table-column>
            <el-table-column
                    prop="roleName"
                    label="角色名称"
                    align="center"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="mark"
                    align="center"
                    label="备注">
            </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit()">提 交</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';

    export default {
        name: 'user-add-role',
        data(){
            return {
                visible: false,
                submitBtnDisabled: false,
                tableData: [],
                selection: []
            }
        },
        methods: {
            submit(){
                this.$emit('select-row', this.selection);
            },
            open(rows){
                this.visible = true;
                RestUtil.get('role/list', {pageNumber: 1, pageSize: 1000}).then((result) => {
                    if(result && result.result && result.result.rows instanceof Array && result.result.rows.length > 0){
                        this.tableData = result.result.rows;
                        let rmap = {};
                        for(let a=0; a<rows.length; a++){
                            let r = rows[a];
                            rmap[r.roleId] = r.roleId;
                        }
                        let candidateSelect = [];
                        for(let i=0; i<this.tableData.length; i++){
                            let row = this.tableData[i];
                            if(rmap[row.roleId]){
                                candidateSelect.push(row);
                            }
                        }
                        setTimeout(() => {
                            this.handleCurrentChange(candidateSelect);
                        }, 200);
                    }
                });
            },
            close(){
                this.visible = false;
            },
            selectRow(selection){
                this.selection = selection;
            },
            handleCurrentChange(rows){
                if(rows instanceof Array){
                    for(let i=0; i<rows.length; i++){
                        this.$refs['roleTable'].toggleRowSelection(rows[i], true);
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
