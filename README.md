# oEmbed를 활용한 간단한 toy-project
oEmbed를 이용하여서 각 Provider 별로 제공하는 컨텐츠를 json 형태로 받아와 화면에 출력해주었다.

AWS 배포: http://52.79.222.224/main  
<br>
<br>
**oEmbed란?**  
> oEmbed는 웹사이트의 콘텐츠를 다른 페이지에 포함할 수 있도록 설계된 개방형 형식입니다. - 위키백과  
<br>

*다른 사이트의 컨텐츠 (YouTube, TikTok..)를 사람들에게 소게하거나 공유하고 싶다면 어떻게 해야 할까?*  

링크(URL)를 공유하는 것도 좋지만 바로 볼 수 있도록 게시물에 **embed** 하는게 효과적일 것이다.  
<ins>**여러 사이트로 부터 쉽게 embed 할 수 있도록 통일된 형식을 사용하는데 이것이 oEmbed이다.**</ins>  
oEmbed를 이용하면 사이트별로 각각 제공하는 API를 활용해서 컨텐츠를 보여주는 것이 아니라. 하나의 통일된 형식으로 컨텐츠를 요청하고 응답을 받는다.  
Provider별로 API 엔드포인트를 제공하고 있어서 해당 url로 컨텐츠 요청을 하면 json/xml 형태로 응답을 보내준다.  

### 개발환경
`Spring Boot`  
`Bootstrap`  
`jQuery`
