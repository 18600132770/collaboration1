<template>
  <span>
    <a-button @click="addNew" type="primary" icon="plus">{{
      $t('add')
    }}</a-button>
    <a-modal v-model="visible" :title="$t('add')" :width="900" :footer="null">
      <StepForm></StepForm>
    </a-modal>
  </span>
</template>

<script>
import StepForm from './StepForm'

export default {
  name: 'AddProject',
  components: {StepForm},
  i18n: require('../i18n'),
  data() {
    return {
      visible: false
    }
  },
  methods: {
    addNew
  }
}

function addNew() {
  this.visible = true
}
</script>

<style lang="less" scoped></style>
