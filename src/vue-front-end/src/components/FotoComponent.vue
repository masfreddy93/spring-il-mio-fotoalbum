<template>
  <div class="container">
    <h2>Fotos</h2>
    <!-- Input search -->
    <input type="text" placeholder="Search for title or tag" v-model="inputSearch">

    <!-- All Fotos -->
    <ul v-if="fotos.length > 0">
      <li
        v-for="foto in fotosOnSearch"
        :key="foto.id"
      >
        <h4>{{ foto.title }}</h4>
        <img :src="foto.url" alt="pic">
        <p>{{ foto.tag }}</p>
      </li>
    </ul>
    <p v-else>No fotos in db</p>
  </div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/1'

export default {
  name: 'FotoComponent',
  data(){
    return {
      fotos: [],
      inputSearch: ''
    }
  },
  computed: {
    fotosOnSearch(){
      return this.fotos.filter(el => {

        let title_and_tag = el.title + " " + el.tag
        return title_and_tag.toLowerCase().includes(this.inputSearch.toLowerCase())
      })
    }
  },

  mounted(){
    axios
    .get(API_URL + '/fotos/all')
    .then(res => 
    {
      // console.log(JSON.stringify(res.data))

      const fotos = res.data
      if(fotos == null) return
      this.fotos = fotos
    })
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
img{
  max-height: 160px;
}
</style>
