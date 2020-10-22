<template>
  <span>
    <a-button @click="addNew" type="primary" icon="plus">{{
      $t('add')
    }}</a-button>
    <a-modal v-model="visible" :title="$t('add')" :width="900" :footer="null">
      <StepForm @close="close"></StepForm>
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
    addNew,
    close
  }
}

function addNew() {
  this.visible = true
}
function close () {
  this.visible = false
}
</script>

<style lang="less" scoped></style>
