<template>
  <div>
    카카오 로그인 진행중...
  </div>
</template>

<script>
import axios from "axios";

export default {
  created() {
    const code = new URL(window.location.href).searchParams.get('code');
    this.sendCodeToServer(code);
  },
  methods: {
    async sendCodeToServer(code) {
      const response = await axios.post('http://localhost:8080/member/kakao/doLogin', {code});
      const token = response.data.totken;
      localStorage.setItem('token', token);
      window.location.href = '/';
    }
  }
}
</script>