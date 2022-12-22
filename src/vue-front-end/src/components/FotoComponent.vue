<template>
  <div class="container">
    <h2>Fotos</h2>
    <!-- Input search -->
    <input type="text" placeholder="Search for title or tag" v-model="inputSearch">

    <!-- All Fotos -->
    <ul v-if="fotos.length > 0">
      <li v-for="foto in fotosOnSearch" :key="foto.id">
        <h4>{{ foto.title }}</h4>
        <img :src="foto.url" alt="pic">
        <p>{{ foto.description }}</p>
        <span>{{ foto.tag }}</span>
        <br>

        <!-- Comments -->
        <button @click="showComments(foto.id)">Show comments</button>
        <div v-if="show_comments_in_foto_with_id == foto.id">
          <ul>
            <li
              v-for="comment in foto.comments"
              :key="comment.id"
            >
              <h4
                v-if="comment.author"
              > 
                {{ comment.author }}</h4>
              <h4
                v-else
              >
                Unknown
              </h4>
              <p>{{ comment.text }}</p>
            </li>
          </ul>
          <br>

          <!-- Leave a comment (form)-->
          <form @submit="createComment">
            <input type="text" name="author" v-model="comment_create.author" placeholder="Your name">
            <br>
            <input type="text" name="text" v-model="comment_create.text" placeholder="Your text">
            <br>

            <input type="submit" value="Comment">
          </form>
          <br><br>
          <button @click="show_comments_in_foto_with_id = -1">Hide comments</button>
        </div>
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
  data() {
    return {
      fotos: [],
      inputSearch: '',
      show_comments_in_foto_with_id: -1,
      comment_create: {}
    }
  },
  methods: {

    getFotoIndexById(id) {
      for (let i = 0; i < this.fotos.length; i++) {
        const foto = this.fotos[i]
        if (foto.id == id)
          return i
      }
      return -1
    },
    showComments(id) {

      this.show_comments_in_foto_with_id = id;
      axios
        .get(API_URL + '/comments/by/foto/' + id)
        .then(res => {

          // console.log(JSON.stringify(res.data))
          const comments = res.data
          if(comments == null) 
            return
          
          const index = this.getFotoIndexById(id)
          this.fotos[index].comments = comments
        })
    },
    createComment(e){
      e.preventDefault()

      const id = this.show_comments_in_foto_with_id

      axios
        .post(API_URL + '/comments/create/' + id, this.comment_create)
        .then(res => {

          // console.log(JSON.stringify(res.data))

          const comment = res.data
          if(comment == null)
            return

          const index = this.getFotoIndexById(id)
          this.fotos[index].comments.push(comment)
        })
    }
  },

  computed: {
    fotosOnSearch() {
      return this.fotos.filter(el => {

        let title_and_tag = el.title + " " + el.tag
        return title_and_tag.toLowerCase().includes(this.inputSearch.toLowerCase().trim())
      })
    }
  },

  mounted() {
    axios
      .get(API_URL + '/fotos/all')
      .then(res => {
        // console.log(JSON.stringify(res.data))

        const fotos = res.data
        if (fotos == null) 
          return
        
        this.fotos = fotos
      })
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.container{
  max-width: 900px;
  margin: 0 auto;
}

h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: .5rem;
  border: .5px solid black;
  margin-bottom: .5rem;
}

a {
  color: #42b983;
}

img {
  max-height: 160px;
}
</style>
