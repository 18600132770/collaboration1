<template>
  <a-card :bordered="false" style="padding: 0">
    <!-- <a-steps class="steps" :current="current">
      <a-step :title="$t('input')" />
      <a-step :title="$t('confirm')" />
      <a-step :title="$t('complete')" />
    </a-steps> -->
    <div class="content">
      <ProjectForm v-if="current === 0" @nextStep="nextStep"></ProjectForm>
      <!-- <UploadFile v-if="current === 1" @nextStep="nextStep" @prevStep="prevStep"></UploadFile> -->
      <!-- <step3 v-if="current === 2" @prevStep="prevStep" @finish="finish"></step3> -->
    </div>
  </a-card>
</template>

<script>
import ProjectForm from './ProjectForm'
// import UploadFile from './UploadFile'
// import Step3 from './Step3'

export default {
  name: 'StepForm',
  i18n: require('../i18n'),
  components: {ProjectForm/* , UploadFile, Step3 */},
  data () {
    return {
      current: 0
    }
  },
  computed: {
    desc() {
      return this.$t('pageDesc')
    }
  },
  methods: {
    nextStep () {
      if (this.current < 2) {
        this.current += 1
      }
    },
    prevStep () {
      if (this.current > 0) {
        this.current -= 1
      }
    },
    finish () {
      // this.current = 0
      this.$emit('close')
    }
  }
}
</script>

<style lang="less" scoped>
  .steps{
    max-width: 950px;
    margin: 16px auto;
  }
</style>
