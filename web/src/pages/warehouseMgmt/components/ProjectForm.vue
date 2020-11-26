<template>
  <a-form-model
    ref="ruleForm"
    :model="form"
    :rules="rules"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
  >
    <a-form-model-item ref="materialsName" :label="$t('materialsName')" prop="materialsName">
      <a-input
        v-model="form.materialsName"
        :placeholder="$t('inputMaterialsName')"
      />
    </a-form-model-item>
    <a-form-model-item ref="materialsType" :label="$t('materialsType')" prop="materialsType">
      <a-input v-model="form.materialsType" :placeholder="`请输入${$t('materialsType')}`"/>
    </a-form-model-item>
    <a-form-model-item ref="materialsUnitPrice" :label="$t('materialsUnitPrice')" prop="materialsUnitPrice">
      <a-input v-model="form.materialsUnitPrice" :placeholder="`请输入${$t('materialsUnitPrice')}`"/>
    </a-form-model-item>
    <a-form-model-item :label="$t('materialsUnit')" prop="materialsUnit">
      <a-select v-model="form.materialsUnit" :placeholder="$t('selectmaterialsUnit')">
        <a-select-option value="shanghai">个</a-select-option>
        <a-select-option value="beijing1">包</a-select-option>
        <a-select-option value="beijing2">袋</a-select-option>
        <a-select-option value="beijing3">台</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item ref="materialsPurchaseContract" :label="$t('materialsPurchaseContract')" prop="materialsPurchaseContract">
      <a-input v-model="form.materialsPurchaseContract" :placeholder="`请输入${$t('materialsPurchaseContract')}`"/>
    </a-form-model-item>
    <a-form-model-item ref="materialsSupplier" :label="$t('materialsSupplier')" prop="materialsSupplier">
      <a-input v-model="form.materialsSupplier" :placeholder="`请输入${$t('materialsSupplier')}`"/>
    </a-form-model-item>
    <a-form-model-item :label="$t('materialsInputDate')" required prop="materialsInputDate">
      <a-date-picker
        v-model="form.materialsInputDate"
        show-time
        type="date"
        :placeholder="$t('pickDate')"
        style="width: 100%;"
      />
    </a-form-model-item>
    <a-form-model-item :label="$t('materialsInputNum')" prop="materialsInputNum">
      <a-input v-model="form.materialsInputNum" :placeholder="`请输入${$t('materialsInputNum')}`"/>
    </a-form-model-item>
    <a-form-model-item :label="$t('materialsInputAmount')" prop="materialsInputAmount">
      <a-input v-model="form.materialsInputAmount" :placeholder="`请输入${$t('materialsInputAmount')}`"/>
    </a-form-model-item>
    <a-form-model-item :label="$t('materialsStorageLocation')" prop="materialsStorageLocation">
      <a-select v-model="form.materialsStorageLocation" :placeholder="`请选择${$t('materialsStorageLocation')}`">
        <a-select-option value="qiaoliang">1号库</a-select-option>
        <a-select-option value="suidao">2号库</a-select-option>
        <a-select-option value="luji">3号库</a-select-option>
        <a-select-option value="dizhi">4号库</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item :wrapper-col="{ span: 15, offset: 9 }">
      <a-button type="primary" @click="onSubmit">
        {{$t('submit')}}
      </a-button>
    </a-form-model-item>
  </a-form-model>
</template>

<script>
export default {
  name: 'ProjectForm',
  i18n: require('../i18n'),
  data() {
    return {
      visible: false,
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      form: {
        materialsName: '',
        materialsType: '',
        materialsUnit: '',
        materialsUnitPrice: '',
        materialsPurchaseContract: '',
        materialsSupplier: '',
        materialsInputDate: undefined,
        materialsInputNum: '',
        materialsInputAmount: '',
        materialsStorageLocation: '',
      },
      rules: {
        materialsName: [{ required: true, message: this.$t('inputNo'), trigger: 'blur' }],
        materialsType: [{ required: true, message: this.$t('inputName'), trigger: 'blur' }],
        materialsUnit: [{ required: true, message: this.$t('selectmaterialsUnit'), trigger: 'change' }],
        startTime: [{ required: true, message: this.$t('pickDate'), trigger: 'change' }],
        materialsInputDate: [{ required: true, message: this.$t('pickDate'), trigger: 'change' }],
        materialsInputNum: [{ required: true, message: this.$t('materialsInputNum'), trigger: 'change' }],
        materialsInputAmount: [{ required: true, message: this.$t('materialsInputAmount'), trigger: 'change' }],
        materialsStorageLocation: [{ required: true, message: this.$t('materialsStorageLocation'), trigger: 'change' }],
        
      }
    }
  },
  methods: {
    onSubmit() {
      this.$emit('nextStep')

      /* this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.$emit('nextStep')
        } else {
          console.log('error submit!!');
          return false;
        }
      }) */
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
  },
}

</script>

<style lang="less" scoped></style>
