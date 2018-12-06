
function treeToArray(data, expandAll, parent = null, level = null) {
    let tmp = []
    Array.from(data).forEach(function(record) {
        if (record._expanded === undefined) {
            Vue.set(record, '_expanded', expandAll)
        }
        let _level = 1
        if (level !== undefined && level !== null) {
            _level = level + 1
        }
        Vue.set(record, '_level', _level)
        // 如果有父元素
        if (parent) {
            Vue.set(record, 'parent', parent)
        }
        tmp.push(record)
        if (record.children && record.children.length > 0) {
            const children = treeToArray(record.children, expandAll, record, _level)
            tmp = tmp.concat(children)
        }
    })
    return tmp
}

Vue.component('treeTable', {
    template: '<template>\n' +
    '  <el-table :data="formatData" :row-style="showRow" v-bind="$attrs">\n' +
    '    <el-table-column v-if="columns.length===0" width="150">\n' +
    '      <template slot-scope="scope">\n' +
    '        <span v-for="space in scope.row._level" class="ms-tree-space" :key="space"></span>\n' +
    '        <span class="tree-ctrl" v-if="iconShow(0,scope.row)" @click="toggleExpanded(scope.$index)">\n' +
    '          <i v-if="!scope.row._expanded" class="el-icon-plus"></i>\n' +
    '          <i v-else class="el-icon-minus"></i>\n' +
    '        </span>\n' +
    '        {{scope.$index}}\n' +
    '      </template>\n' +
    '    </el-table-column>\n' +
    '    <el-table-column v-else v-for="(column, index) in columns" :key="column.value" :label="column.text" :width="column.width">\n' +
    '      <template slot-scope="scope">\n' +
    '        <span v-if="index === 0" v-for="space in scope.row._level" class="ms-tree-space" :key="space"></span>\n' +
    '        <span class="tree-ctrl" v-if="iconShow(index,scope.row)" @click="toggleExpanded(scope.$index)">\n' +
    '          <i v-if="!scope.row._expanded" class="el-icon-plus"></i>\n' +
    '          <i v-else class="el-icon-minus"></i>\n' +
    '        </span>\n' +
    '        {{scope.row[column.value]}}\n' +
    '      </template>\n' +
    '    </el-table-column>\n' +
    '    <slot></slot>\n' +
    '  </el-table>\n' +
    '</template>',
    name: 'treeTable',
    props: {
        data: {
            type: [Array, Object],
            required: true
        },
        columns: {
            type: Array,
            default: () => []
        },
        evalFunc: Function,
        evalArgs: Array,
        expandAll: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        // 格式化数据源
        formatData: function() {
            let tmp
            if (!Array.isArray(this.data)) {
                tmp = [this.data]
            } else {
                tmp = this.data
            }
            const func = this.evalFunc || treeToArray
            const args = this.evalArgs ? Array.concat([tmp, this.expandAll], this.evalArgs) : [tmp, this.expandAll]
            return func.apply(null, args)
        }
    },
    methods: {
        showRow: function(row) {
            const show = (row.row.parent ? (row.row.parent._expanded && row.row.parent._show) : true)
            row.row._show = show
            return show ? 'animation:treeTableShow 1s;-webkit-animation:treeTableShow 1s;' : 'display:none;'
        },
        // 切换下级是否展开
        toggleExpanded: function(trIndex) {
            const record = this.formatData[trIndex]
            record._expanded = !record._expanded
        },
        // 图标显示
        iconShow(index, record) {
            return (index === 0 && record.children && record.children.length > 0)
        }
    }

})