<template>
    <a-modal
        :visible="orderRateModalVisible"
        title="订单评价"
        okText="确认"
        cancelText="取消"
        @cancel="cancelOrderRate"
        @ok="submitOrderRate"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" style="margin: 0px auto">
                <div>评分：</div>
                <a-rate :allow-half="true"
                        v-decorator="[
                        'score',
                        { rules: [{ required: true, message: '请评分' }] },
                    ]"></a-rate>
            </a-form-item>
            <br>
            <a-form-item v-bind="formItemLayout" style="width: 500px; margin: auto">
                <div>评价：</div>
                <a-textarea placeholder="请输入评价" :rows="4"
                            v-decorator="[
                        'comment',
                        { rules: [{ required: true, message: '请输入评价' }] },
                    ]"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "orderRateModal",
        data(){
            return{
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
                star : 0,
            }
        },
        computed: {
            ...mapGetters([
                'orderRateModalVisible',
                'currentOrder'
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'orderRateModal'});
        },
        methods: {
            ...mapMutations([
                'set_orderRateModalVisible'
            ]),
            ...mapActions([
               'addComment'
            ]),
            cancelOrderRate() {
                this.set_orderRateModalVisible(false)
            },
            submitOrderRate(e){
                e.preventDefault();
                var mydate = new Date()
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data={
                            orderId:this.currentOrder.id,
                            score:this.form.getFieldValue('score'),
                            content:this.form.getFieldValue('comment')
                        }
                        console.log(data)
                        this.addComment(data)
                    }
                })
            }
        }

    }
</script>

<style scoped>

</style>