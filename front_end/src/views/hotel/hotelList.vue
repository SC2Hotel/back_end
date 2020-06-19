<template>
    <div class="hotelList">
        <a-layout>
            <a-layout-content style="min-width: 800px">
                    <a-input-group compact style="margin-bottom: 50px">
                        <a-select default-value="西单" @change="onChange" >
                            <a-select-option v-for="item in bizRegions" :key="item">
                                {{item}}
                            </a-select-option>
                        </a-select>
                        <a-input style="width: 50%" placeholder="请输入地址（可选）" v-model="addresss"/>
                        <a-button v-on:click="search">搜索</a-button>
                    </a-input-group>
                <a-spin :spinning="hotelListLoading">
                    <div class="card-wrapper">
                        <div v-if="hotelList.length==0">抱歉，该地址暂无酒店信息</div>
                        <div v-else>
                            <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index"
                                       @click.native="jumpToDetails(item.id)"></HotelCard>
                            <div v-for="item in emptyBox" :key="item.name"
                                 class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                            </div>
<!--                            <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1"-->
<!--                                          @change="pageChange"></a-pagination>-->
                        </div>
                    </div>
                    <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1"
                                  @change="pageChange"></a-pagination>
                </a-spin>
            </a-layout-content>
        </a-layout>
    </div>
</template>
<script>
    import HotelCard from './components/hotelCard'
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        name: 'home',
        components: {
            HotelCard
        },
        data() {
            return {
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                options: this.bizRegions,
                selected: "西单",
                addresss:"",
                searchCondition:"",
            }
        },
        async mounted() {
            await this.getHotelList()
            await this.getBizregions()
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'hotelListLoading',
                'bizRegions'
            ]),
            filterhotelList: function () {
                var selected = this.selected;
                var hotellist = this.hotelList;
                return hotellist.filter(function (item) {
                    return item.bizRegion == selected
                })
            }
        },
        methods: {
            ...mapMutations([
                'set_hotelListParams',
                'set_hotelListLoading'
            ]),
            ...mapActions([
                'getHotelList',
                'getBizregions',
                'getHotelByBizAndAdd'
            ]),

            pageChange(page, pageSize) {
                const data = {
                    pageNo: page - 1
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getHotelList()
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            onChange(value) {
                this.selected = value
                this.getHotelByBizAndAdd({bizRegion:this.selected,address:this.addresss})
            },
            search(){
                this.getHotelByBizAndAdd({bizRegion:this.selected,address:this.addresss})
            }
        }
    }
</script>
<style scoped lang="less">
    .hotelList {
        text-align: center;
        padding: 50px 0;

        .emptyBox {
            height: 0;
            margin: 10px 10px
        }

        .card-wrapper {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 800px
        }

        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }
</style>