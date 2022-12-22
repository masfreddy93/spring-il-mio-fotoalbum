<template>
  <main>
    <div class="container">
      <h2>Fotos</h2>
      <!-- Input search -->
      <p>
        <input 
          v-model="inputSearch" 
          type="text" placeholder="Search for title or tag" class="input-search"
        >
      </p>
  
      <!-- All Fotos -->
      <ul 
        v-if="fotos.length > 0"
      >
        <li 
          v-for="foto in fotosOnSearch" 
          :key="foto.id"
          class="foto-card"
        >
          <!-- Foto details -->
          <h4>{{ foto.title }}</h4>
          <figure>
            <img 
              :src="foto.url" 
              alt="pic"
            >
          </figure>
          <p>{{ foto.description }}</p>
          <span>{{ foto.tag }}</span>
  
          <!-- Comments -->
          <button 
            @click="showComments(foto.id)"
            v-if="show_comments_in_foto_with_id != foto.id"
          >Show comments</button>
          <div 
            v-if="show_comments_in_foto_with_id == foto.id"
          >
            <button 
              @click="show_comments_in_foto_with_id = -1"
            >Hide comments</button>
            <div class="comments-cont">
              <ul 
                v-if="foto.comments && foto.comments.length > 0"
              >
                <li
                  v-for="comment in foto.comments"
                  :key="comment.id"
                  class="comment-card"
                >
                  <h4 v-if="comment.author">{{ comment.author }}</h4>
                  <h4 v-else>Anonymous user</h4>
                  <p>{{ comment.text }}</p>
                </li>
              </ul>
              <p 
                v-else>There are no comments. Write the firtst one!
              </p>
              <br>
            </div>
  
            <!-- Leave a comment (form)-->
            <!-- errors -->
            <p 
              v-if="errors.length" 
              style="color: red;"
              class="errors-cont"
            >
              <ul>
                <li 
                  v-for="error in errors" 
                  :key="error.id"
                  class="error-card"
                >{{ error }}</li>
              </ul>
            </p>
            <!-- form -->
            <form 
              @submit="createComment"
              class="comments-form"
            >
              <input v-model="comment_create.author" type="text" name="author" placeholder="Your name">
              <br>
              <textarea rows="5" cols="30" v-model="comment_create.text" type="text" name="text" placeholder="Your text"></textarea>
              <br>
  
              <input type="submit" value="Comment">
            </form>
          </div>
        </li>
      </ul>
  
      <!-- If there are no visible fotos in DB -->
      <p v-else>No fotos in db</p>
    </div>
  </main>
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
      comment_create: {},
      errors: []
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

      // --------------------------------------------------------------------
      //validation
      this.errors = []

      if (this.comment_create.author && this.comment_create.author.length > 255) 
        this.errors.push('Author name cannot contain more than 255 letters')
      if (!this.comment_create.text) 
        this.errors.push('Text comment is required')

      if(this.errors.length) return
      // --------------------------------------------------------------------
      
      // --------------------------------------------------------------------
      //api
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
      // --------------------------------------------------------------------
      
      //clear input 
      this.comment_create.author = ''
      this.comment_create.text = ''
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

        //second check of visibility (backend previously)
        let visibleFotos = [];
        for(let i = 0; i < fotos.length; i++){
          if(fotos[i].visible)
            visibleFotos.push(fotos[i])
        }
        // console.log(visibleFotos)


        if (!visibleFotos.length) 
          return
        
        this.fotos = visibleFotos
      })
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">


.container{
  max-width: 900px;
  margin: 0 auto;

  h2{

    font-size: 40px;
    text-transform: uppercase;
    text-align: center;
  }

  p{
    text-align: center;

    .input-search {
      width: 200px;
      padding: 5px 10px;
      margin-bottom: 2rem;
    }
  }

  ul{
    display: flex;
    flex-direction: column;
    align-items: center;
    flex-basis: 100%;
    gap: 1rem;
    .foto-card{
      border: .5px solid rgb(175, 231, 189);
      border-radius: 8px;
      padding: 1rem .5rem;
      
      h4{
        text-align: center;
        text-transform: uppercase;
        color: rgb(17, 103, 70);
        padding: 4px;
        border-top: .5px solid rgb(100, 167, 138);
        border-left: .5px solid rgb(202, 250, 214);
        border-right: .5px solid rgb(202, 250, 214);
      }

      img {
        height: 300px;
        aspect-ratio: 1;
        object-fit: cover;
        border-radius: 4px 8px;
        display: block;
        margin-bottom: .5rem;
      }

      p{
        text-align: justify;
        font-size: 16px;
        margin-bottom: .5rem;
      }
      
      span{

        color: rgb(107, 107, 190);
        font-size: .875rem;
        font-weight: bold;
        border-bottom: .5px solid rgb(41, 82, 49);
        display: block;
        margin-bottom: .5rem;
      }

      button{
        margin-bottom: 1rem;
        background-color: rgb(141, 189, 173);
      }

      .comments-cont ul{
        align-items: flex-start;
        gap: .25rem;
        .comment-card{
          padding: .1rem .25rem;
          border-bottom: .5px solid rgb(138, 131, 131);
          width: 100%;

          h4{
            font-size: .80rem;
            margin-bottom: .1rem;
            text-align: start;
            border: none;
          }

          p{
            font-size: .75rem;
          }
        }
      }

      .errors-cont ul{

        display: block;
      }
      .comments-form{

        input, textarea{

          padding: 5px 10px;
          margin-bottom: .25rem;
        }
        
        input[type="submit"]{
          cursor: pointer;
        }

      }
    }
  }

  //buttons
  button{

    width: fit-content;
    padding: 5px 10px;
    text-align: justify;

    &:hover{

      cursor: pointer;
      opacity: .5;
    }
  } 
}

</style>
