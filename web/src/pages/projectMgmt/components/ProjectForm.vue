<template>
  <a-form-model
    ref="ruleForm"
    :model="form"
    :rules="rules"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
  >
    <a-form-model-item ref="projectNo" :label="$t('projectNo')" prop="projectNo">
      <a-input
        v-model="form.projectNo"
        :placeholder="$t('inputNo')"
      />
    </a-form-model-item>
    <a-form-model-item ref="projectName" :label="$t('projectName')" prop="projectName">
      <a-input v-model="form.projectName" :placeholder="$t('inputName')"/>
    </a-form-model-item>
    <a-form-model-item :label="$t('designPhase')" prop="designPhase">
      <a-select v-model="form.designPhase" :placeholder="$t('selectDesignPhase')">
        <a-select-option value="shanghai">投标</a-select-option>
        <a-select-option value="beijing1">前期方案研究</a-select-option>
        <a-select-option value="beijing2">初步设计</a-select-option>
        <a-select-option value="beijing3">施工图设计</a-select-option>
        <a-select-option value="beijing4">施工配合</a-select-option>
        <a-select-option value="beijing5">结算</a-select-option>
        <a-select-option value="beijing6">完结</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item :label="$t('startTime')" required prop="startTime">
      <a-date-picker
        v-model="form.startTime"
        show-time
        type="date"
        :placeholder="$t('pickDate')"
        style="width: 100%;"
      />
    </a-form-model-item>
    <a-form-model-item :label="$t('endTime')" required prop="endTime">
      <a-date-picker
        v-model="form.endTime"
        show-time
        type="date"
        :placeholder="$t('pickDate')"
        style="width: 100%;"
      />
    </a-form-model-item>
    <a-form-model-item :label="$t('principal')" prop="principal">
      <a-select v-model="form.principal" :placeholder="$t('selectPrincipal')">
        <a-select-option value="shanghai">张三</a-select-option>
        <a-select-option value="beijing">李四</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item :label="$t('chiefEngineer')" prop="chiefEngineer">
      <a-select v-model="form.chiefEngineer" :placeholder="$t('selectChiefEngineer')">
        <a-select-option value="shanghai">张三</a-select-option>
        <a-select-option value="beijing">李四</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item :label="$t('department')" prop="department">
      <a-select v-model="form.department" :placeholder="$t('selectDepartment')">
        <a-select-option value="qiaoliang">桥梁所</a-select-option>
        <a-select-option value="suidao">隧道所</a-select-option>
        <a-select-option value="luji">路基所</a-select-option>
        <a-select-option value="dizhi">地质所</a-select-option>
      </a-select>
    </a-form-model-item>
    <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">
        {{$t('nextStep')}}
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
        projectNo: '',
        projectName: '',
        designPhase: '',
        startTime: undefined,
        endTime: undefined,
        principal: '',
        chiefEngineer: '',
        department: '',
      },
      rules: {
        projectNo: [{ required: true, message: this.$t('inputNo'), trigger: 'blur' }],
        projectName: [{ required: true, message: this.$t('inputName'), trigger: 'blur' }],
        designPhase: [{ required: true, message: this.$t('selectDesignPhase'), trigger: 'change' }],
        startTime: [{ required: true, message: this.$t('pickDate'), trigger: 'change' }],
        endTime: [{ required: true, message: this.$t('pickDate'), trigger: 'change' }],
        principal: [{ required: true, message: this.$t('principal'), trigger: 'change' }],
        chiefEngineer: [{ required: true, message: this.$t('chiefEngineer'), trigger: 'change' }],
        department: [{ required: true, message: this.$t('department'), trigger: 'change' }],
        
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
