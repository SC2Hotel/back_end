<template>
    <a-modal
            :visible="registerVIPModalVisible"
            okText="确定"
            cancelText="取消"
            @ok="submitRegister"
            @cancel="cancelRegister"
            title="注册会员"
    >
        <div>
            <a-tabs default-active-key="1" @change="changeTypeCommon">
                <a-tab-pane key="1" tab="普通会员">
                    <a-form :form="form">
                        <a-form-item label="请选择生日" v-bind="formItemLayout">
                            <a-date-picker
                                    v-decorator="[
                            'birthday',
                            { rules: [{ required: true, message: '请选择生日' }] },
                        ]" @change="changeDate"/>
                        </a-form-item>
                    </a-form>
                </a-tab-pane>
                <a-tab-pane key="2" tab="企业会员" force-render>
                    <a-form :form="form">
                        <a-form-item label="请输入企业名称" v-bind="formItemLayout">
                            <a-input v-decorator="[
                            'compName',
                            { rules: [{ required: true, message: '请输入企业名称' }] },
                        ]">
                            </a-input>
                        </a-form-item>
                    </a-form>
                </a-tab-pane>
            </a-tabs>
        </div>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "registerVIPModal",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                type: 1,
                date:''
            }
        },
        computed: {
            ...mapGetters([
                'registerVIPModalVisible',
                'userId',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'registerVIPModal'});
        },
        methods: {
            ...mapMutations([
                'set_registerVIPModalVisible'
            ]),
            ...mapActions([
                'registerVIP'
            ]),
            submitRegister(e) {
                e.preventDefault();
                const data = {
                    id: this.userId,
                    type: this.type==1?1:2,
                    message: this.type==1?this.date:this.form.getFieldValue('compName')
                }
                console.log(data)
                this.registerVIP(data)
                this.set_registerVIPModalVisible(false)
            },
            cancelRegister() {
                this.set_registerVIPModalVisible(false)
            },
            changeTypeCommon(key) {
                this.type=key
                console.log(this.type)
            },
            changeDate(date, dateString){
                this.date=dateString
                console.log(dateString)
            }
        }
    }
</script>

<style scoped>

</style>