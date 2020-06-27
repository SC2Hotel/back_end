<template>
    <a-modal
            :visible="updataUserInfoModalVisible"
            title="更新账号信息"
            cancelText="取消"
            okText="更新"
            @cancel="cancelupadate"
            @ok="handleUpdate"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout"  label="用户名"
                         >
                <a-input v-decorator="['userName']"></a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="邮箱">
                <a-input v-decorator="['email']"></a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="信用值">
                <a-input v-decorator="['credit']"></a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input v-decorator="['phoneNum']"></a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="用户类型">
                <a-select v-decorator="['userType']" @change="changeUserType" >
                    <a-select-option value="HotelManager">
                        管理员
                    </a-select-option>
                    <a-select-option value="Client">
                        普通用户
                    </a-select-option>
                    <a-select-option value="commonSeniorClient">
                        个人会员
                    </a-select-option>
                    <a-select-option value="companySeniorClient">
                        企业会员
                    </a-select-option>
                </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "updateUserInfomodal",
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
                form: this.$form.createForm(this,{name:"userInfoUpadateform"}),
                userType:""
            }
        },
        computed: {
            ...mapGetters([
                'updataUserInfoModalVisible',
                'targetUserInfo'
            ])
        },
        methods: {
            ...mapMutations([
                'set_updataUserInfoModalVisible'
            ]),
            ...mapActions([
                'updateOtherAccount'
            ]),
            cancelupadate(){
                this.set_updataUserInfoModalVisible(false)
            },
            changeUserType(value){
                this.userType = value
            },
            handleUpdate(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            id: this.targetUserInfo,
                            email:this.form.getFieldValue('email'),
                            phoneNumber:this.form.getFieldValue('phoneNum'),
                            userType: this.form.getFieldValue('userType'),
                            userName: this.form.getFieldValue('userName'),
                            credit: this.form.getFieldValue('credit'),
                            password: ""
                        }
                        this.updateOtherAccount(data)
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>