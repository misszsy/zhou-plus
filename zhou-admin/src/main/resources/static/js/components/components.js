Vue.component("icon-list",{
    template:`<el-tabs type="border-card"> 
                <el-tab-pane label="官方图标">
                    <ul class="icon-list">
                        <li @dblclick="dblclick"  v-for="icon in iconList" v-if="icon.type==0"  :class="icon.name === name ? 'active' : 'default' ">
                             <span>
                                <i :class="icon.name"></i>
                                <span class="icon-name">{{icon.name}}</span>
                             </span>
                         </li>
                    </ul>
                </el-tab-pane>
                <el-tab-pane label="第三方图标">
                    <ul class="icon-list">
                        <li  @dblclick="dblclick" v-for="icon in iconList" v-if="icon.type==1"  :class="icon.name === name ? 'active' : 'default' ">
                             <span>
                                <i :class="icon.name"></i>
                                <span class="icon-name">{{icon.name}}</span>
                             </span>
                         </li>    
                    </ul>
                </el-tab-pane>
               </el-tabs>`,
    props: {
        list:Array,
        name:String,
    },
    data(){
        return {
            iconList:[],
            iconName:'',
        }
    },
    mounted() {
        this.iconList=this.list;
    },
    methods:{
        dblclick(e){
            this.$emit('dblclick',e);
        }
    }
})