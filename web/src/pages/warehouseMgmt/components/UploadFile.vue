<template>
  <div>
    <div class="tree-container">
      <FileTree></FileTree>
    </div>
    <a-form style="max-width: 500px; margin: 10px auto 0;">
      <a-form-item :wrapperCol="{span: 17, offset: 7}">
        <a-button :loading="loading" type="primary" @click="nextStep">{{$t('submit')}}</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">{{$t('preStep')}}</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import FileTree from './FileTree'
export default {
  i18n: require('../i18n'),
  components: {FileTree},
  methods: {
    nextStep () {
      let _this = this
      _this.loading = true
      setTimeout(function () {
        _this.$emit('nextStep')
      }, 1500)
    },
    prevStep () {
      this.$emit('prevStep')
    }
  }
}
</script>

<style lang="less" scoped>
  .tree-container {
    max-height: 500px;
    width: 100%;
    // overflow-y: scroll;
    overflow-x: hidden;
  }
</style>