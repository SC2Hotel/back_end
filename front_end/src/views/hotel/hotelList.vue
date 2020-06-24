<template>
    <div class="hotelList">
        <a-layout>
            <a-layout-content style="min-width: 1000px">
                <div style="margin-bottom: 50px; display: flex;justify-content: start">
                    <a-input-group compact>
                        <a-select default-value="西单" @change="onChange">
                            <a-select-option v-for="item in bizRegions" :key="item">
                                {{item}}
                            </a-select-option>
                        </a-select>
                        <a-input style="width: 400px" placeholder="请输入地址（可选）" v-model="address"/>
                        <a-button v-on:click="searchByBiz">搜索</a-button>
                        <!--                    </a-input-group>-->
                        <!--                    <a-input-group>-->
                        <a-button type="primary" @click="searchExactly">精确查找</a-button>
                        <search-modal></search-modal>
                    </a-input-group>
                    <a-input-group>
                        <a-checkbox @change="chooseItem">
                            是否曾经预订
                        </a-checkbox>
                    </a-input-group>
                </div>
                <a-spin :spinning="hotelListLoading">
                    <div class="card-wrapper">
                        <div v-if="hotelList.length==0">抱歉，该地址暂无酒店信息</div>
                        <div v-else>
                            <HotelCard :hotel="item" v-for="(item,index) in hotelList" :key="index"
                                       @click.native="jumpToDetails(item.id)"></HotelCard>
                            <div v-for="item in emptyBox" :key="item.name"
                                 class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                            </div>
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
    import SearchModal from "./components/searchModal";

    export default {
        name: 'home',
        components: {
            SearchModal,
            HotelCard
        },
        data() {
            return {
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                options: this.bizRegions,
                oriHotelList: this.getHotelList(),
                selected: "西单",
                address: "",
                bookedonce: false
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
                'bizRegions',
                'userInfo'
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
                'set_hotelListLoading',
                'set_searchModalVisible',
            ]),
            ...mapActions([
                'getHotelList',
                'getBizregions',
                'getHotelByBizAndAdd',
                'getBookedHotels'
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
                this.getHotelByBizAndAdd({bizRegion: this.selected, address: this.address})
            },
            searchByBiz() {
                console.log(this.selected)
                this.getHotelByBizAndAdd({bizRegion: this.selected, address: this.address})
            },
            searchExactly() {
                this.set_searchModalVisible(true)
            },
            chooseItem(e) {
                this.bookedonce = e.target.checked
                if (this.bookedonce) {
                    this.getBookedHotels(this.userInfo.id)
                }
                else {
                    this.getHotelList()
                }
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