<template>
  <div class="base-form">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      :label-width="labelWidth"
      :label-position="labelPosition"
    >
      <el-form-item
        v-for="(item, index) in formItems"
        :key="index"
        :label="item.label"
        :prop="item.prop"
        :rules="item.rules"
      >
        <!-- 输入框 -->
        <el-input
          v-if="item.type === 'input'"
          v-model="formData[item.prop]"
          :placeholder="item.placeholder"
          :disabled="item.disabled"
          :clearable="item.clearable"
          :type="item.inputType || 'text'"
        />

        <!-- 选择器 -->
        <el-select
          v-else-if="item.type === 'select'"
          v-model="formData[item.prop]"
          :placeholder="item.placeholder"
          :disabled="item.disabled"
          :clearable="item.clearable"
          :multiple="item.multiple"
        >
          <el-option
            v-for="option in item.options"
            :key="option.value"
            :label="option.label"
            :value="option.value"
          />
        </el-select>

        <!-- 日期选择器 -->
        <el-date-picker
          v-else-if="item.type === 'date'"
          v-model="formData[item.prop]"
          :type="item.dateType || 'date'"
          :placeholder="item.placeholder"
          :disabled="item.disabled"
          :clearable="item.clearable"
          :format="item.format"
          :value-format="item.valueFormat"
        />

        <!-- 单选框组 -->
        <el-radio-group
          v-else-if="item.type === 'radio'"
          v-model="formData[item.prop]"
          :disabled="item.disabled"
        >
          <el-radio
            v-for="option in item.options"
            :key="option.value"
            :label="option.value"
          >
            {{ option.label }}
          </el-radio>
        </el-radio-group>

        <!-- 复选框组 -->
        <el-checkbox-group
          v-else-if="item.type === 'checkbox'"
          v-model="formData[item.prop]"
          :disabled="item.disabled"
        >
          <el-checkbox
            v-for="option in item.options"
            :key="option.value"
            :label="option.value"
          >
            {{ option.label }}
          </el-checkbox>
        </el-checkbox-group>

        <!-- 开关 -->
        <el-switch
          v-else-if="item.type === 'switch'"
          v-model="formData[item.prop]"
          :disabled="item.disabled"
        />

        <!-- 文本域 -->
        <el-input
          v-else-if="item.type === 'textarea'"
          v-model="formData[item.prop]"
          type="textarea"
          :rows="item.rows || 3"
          :placeholder="item.placeholder"
          :disabled="item.disabled"
        />

        <!-- 自定义插槽 -->
        <slot
          v-else
          :name="item.prop"
          :form-data="formData"
          :item="item"
        />
      </el-form-item>

      <!-- 表单按钮 -->
      <el-form-item v-if="showButtons">
        <el-button
          type="primary"
          :loading="submitLoading"
          @click="handleSubmit"
        >
          {{ submitText }}
        </el-button>
        <el-button @click="handleReset">{{ resetText }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { defineComponent, ref, reactive, watch } from 'vue'

export default defineComponent({
  name: 'BaseForm',
  props: {
    // 表单项配置
    formItems: {
      type: Array,
      default: () => []
    },
    // 表单数据
    modelValue: {
      type: Object,
      default: () => ({})
    },
    // 表单验证规则
    rules: {
      type: Object,
      default: () => ({})
    },
    // 标签宽度
    labelWidth: {
      type: String,
      default: '100px'
    },
    // 标签位置
    labelPosition: {
      type: String,
      default: 'right'
    },
    // 是否显示按钮
    showButtons: {
      type: Boolean,
      default: true
    },
    // 提交按钮文本
    submitText: {
      type: String,
      default: '提交'
    },
    // 重置按钮文本
    resetText: {
      type: String,
      default: '重置'
    },
    // 提交按钮加载状态
    submitLoading: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:modelValue', 'submit', 'reset'],
  setup(props, { emit }) {
    // 表单ref
    const formRef = ref(null)
    // 表单数据
    const formData = reactive({})

    // 监听表单数据变化
    watch(
      () => props.modelValue,
      (newVal) => {
        Object.assign(formData, newVal)
      },
      { immediate: true, deep: true }
    )

    // 监听formData变化，同步到父组件
    watch(
      formData,
      (newVal) => {
        emit('update:modelValue', newVal)
      },
      { deep: true }
    )

    // 提交表单
    const handleSubmit = async () => {
      if (!formRef.value) return
      try {
        await formRef.value.validate()
        emit('submit', formData)
      } catch (error) {
        console.error('表单验证失败:', error)
      }
    }

    // 重置表单
    const handleReset = () => {
      if (!formRef.value) return
      formRef.value.resetFields()
      emit('reset')
    }

    return {
      formRef,
      formData,
      handleSubmit,
      handleReset
    }
  }
})
</script>

<style scoped>
.base-form {
  width: 100%;
  padding: 20px;
}
</style> 