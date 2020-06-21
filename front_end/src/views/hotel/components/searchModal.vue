<template>
    <a-modal
            :visible="searchModalVisible"
            title="精确搜索"
            cancelText="取消"
            okText="搜索"
            @cancel="cancelsearch"
            @ok="handleSubmit">
        <a-form :form="form">
            <a-form-item label="商圈" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'bizRegion',
                        { rules: [{ required: true, message: '请选择商圈' }] },
                    ]"
                        placeholder="请选择商圈">
                    <a-select-option v-for="(item,index) in bizRegions" :key="index" :value="item">
                        {{item}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'roomType',
                        { rules: [{ required: true, message: '请选择房型' }] },
                    ]"
                        placeholder="请选择房型">
                    <a-select-option value="BigBed">
                        大床房
                    </a-select-option>
                    <a-select-option value="DoubleBed">
                        双床房
                    </a-select-option>
                    <a-select-option value="Family">
                        家庭房
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="地址" v-bind="formItemLayout">
                <a-input
                            v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店名" v-bind="formItemLayout">
                <a-input
                        v-decorator="['hotelName']"
                />
            </a-form-item>
            <a-form-item label="星级" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'hotelStar',
                        { rules: [{ required: true, message: '请选择星级' }] },
                    ]"
                        placeholder="请选择星级">
                    <a-select-option value="Three">
                        3
                    </a-select-option>
                    <a-select-option value="Four">
                        4
                    </a-select-option>
                    <a-select-option value="Five">
                        5
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="酒店评分不低于">
                <a-input-number
                        :max="5"
                        :min="0"
                    v-decorator="[
                        'rate'
                    ]"
                />
            </a-form-item>
            <a-form-item label="价格区间" v-bind="formItemLayout">
                <a-input-number
                        :min="0"
                        v-decorator="[
                        'loPrice'
                    ]"
                />~
                <a-input-number
                        :min="0"
                        v-decorator="[
                        'hiPrice'
                    ]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    const MAXPRICE = 999999

    export default {
        name: "searchModal",
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
            }
        },
        computed: {
            ...mapGetters([
                'searchModalVisible',
                'bizRegions'
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'searchModal'});
        },
        methods: {
            ...mapMutations([
                'set_searchModalVisible'
            ]),
            ...mapActions([
                'getHotelExactly',
                'getBizregions',
            ]),
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            roomType: this.form.getFieldValue('roomType'),
                            address: this.form.getFieldValue('address'),
                            name: this.form.getFieldValue('hotelName')==undefined?"":this.form.getFieldValue('hotelName'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            rate: this.form.getFieldValue('rate')==undefined?0:this.form.getFieldValue('rate'),
                            loPrice:this.form.getFieldValue('loPrice')==undefined?0:this.form.getFieldValue('loPrice'),
                            hiPrice:this.form.getFieldValue('hiPrice')==undefined?MAXPRICE:this.form.getFieldValue('hiPrice')
                        }
                        this.getHotelExactly(data)
                    }
                });
            },
            cancelsearch() {
                this.set_searchModalVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>