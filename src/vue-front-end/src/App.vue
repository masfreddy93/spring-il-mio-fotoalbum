<template>
  <FotoComponent />
</template>

<script>
import FotoComponent from './components/FotoComponent.vue'

export default {
  name: 'App',
  components: {
    FotoComponent
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  // text-align: center;
  // margin-top: 60px;
  background: linear-gradient(#dfe6e2, #b8c4bd);
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

ul,
ol,
menu {
  list-style-type: none;
}

a {
  color: currentColor;
  text-decoration: none;
}
</style>
