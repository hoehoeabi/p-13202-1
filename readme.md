## 1차 요구사항 구현
- [x] 유저가 루트 url로 접속시에 게시글 리스트 페이지(http://주소:포트/article/list)가 나온다.
- [x] 리스트 페이지에서는 등록 버튼이 있고 버튼을 누르면 http://주소:포트/article/create 경로로 이동하고 등록 폼이 나온다.
- [x] 게시글 등록을 하면 http://주소:포트/article/create로 POST 요청을 보내어 DB에 해당 내용을 저장한다.
- [x] 게시글 등록이 되면 해당 게시글 리스트 페이지로 리다이렉트 된다. 페이지 URL 은 http://주소:포트/article/list 이다.
- [x] 리스트 페이지에서 해당 게시글을 클릭하면 상세페이지로 이동한다. 해당 경로는 http://주소:포트/article/detail/{id} 가 된다.
- [x] 게시글 상세 페이지에는 목록 버튼이 있다. 목록 버튼을 누르면 게시글 리스트 페이지로 이동하게 된다.

# 2차 요구사항
- [x] 게시글 상세페이지(http://주소:포트/article/detail/{id})에 수정 버튼이 있다. 수정 버튼을 누르면 게시글을 수정 할 수 있는 폼이나 오고 수정이 가능하다.
- [x] 게시글 상세페이지에 삭제 버튼이 있다. 삭제 버튼을 누르면 게시글이 삭제가 된다. 삭제 후 리스트 페이지로 리다이렉트 된다.
- [x] 모든 페이지 상단에 루트 디렉토리로 이동하는 버튼이 있다.(예: 로고)
- [x] 모든 페이지 상단에 로그인 상태 표시하는 버튼이 있다.(예: 로그인 / 로그아웃)
- [x] 모든 페이지 회원가입 버튼이 있다. 버튼을 누르면 회원가입 폼으로 이동한다.
    - [x] 회원가입 폼은 유저ID, 닉네임, 비빌번호, 비밀번호 확인으로 구성된다. 회원가입 버튼을 누르면 데이터 검증 후 회원가입이 된다.
- [x] 로그인 버튼을 누르면 로그인 폼으로 이동한다.
    - [x] 로그인 페이지는 사용자 유저ID과 비밀번호를 입력하는 폼으로 구성되고 로그인 버튼을 누르면 데이터 검증 후 로그인이 된다.
- [x] 로그아웃 버튼을 누르면 로그아웃이 된다.
- [x] 유저가 게시글 작성 및 수정  접근시 로그인 여부를 검사하고 본인 글에 대해서만 수정 / 삭제가 가능하다.
- [x] (선택)메인페이지에 검색 기능이 구현되어야 한다. input 박스에 내용을 적고 검색 버튼을 누르면 해당 문자가 포함된 게시글이 리스트업 되어야 한다.
  본인 글에 대해서만 수정 / 삭제가 가능하다.

## 미비사(선택)항 or 막힌 부분
- 타임리프는 ai와 함께 했습니다

## UI/UX (화면 캡처본을 복사 붙여 넣기, url 주소 나오도록)
- 게시글 리스트 페이지
  - 로그인
<img width="1042" height="507" alt="스크린샷 2026-05-03 오전 1 47 06" src="https://github.com/user-attachments/assets/9e5bb042-35f7-4c88-a39e-bc24e2f412c4" />
  - 비로그인
<img width="1042" height="330" alt="스크린샷 2026-05-03 오전 1 48 55" src="https://github.com/user-attachments/assets/66d72a64-72e2-4e90-b9cd-7e54fea53a6f" />

- 게시글 등록 폼 페이지
<img width="1042" height="603" alt="스크린샷 2026-05-03 오전 1 47 23" src="https://github.com/user-attachments/assets/59b856d9-0554-4441-8fdb-3418791a2ccc" />

- 게시글 상세 페이지
  - 글 주인
<img width="1042" height="386" alt="스크린샷 2026-05-03 오전 1 47 44" src="https://github.com/user-attachments/assets/bfd19ead-b2d9-4f45-86b8-8a4441afa8cd" />
  - 비로그인 혹은 글주인이 아닐경우
  <img width="1042" height="374" alt="스크린샷 2026-05-03 오전 1 49 37" src="https://github.com/user-attachments/assets/c97c46b4-9af0-442d-9d25-3dca8be3866f" />


- 게시물 수정 페이지
<img width="1042" height="611" alt="스크린샷 2026-05-03 오전 1 48 14" src="https://github.com/user-attachments/assets/366ff537-9ec9-4f7d-a566-5585960793ed" />

- 로그인 페이지
<img width="1042" height="485" alt="스크린샷 2026-05-03 오전 1 45 33" src="https://github.com/user-attachments/assets/44bbd390-f342-43b2-bc3b-e508ef07f13b" />
<img width="1042" height="507" alt="스크린샷 2026-05-03 오전 1 46 50" src="https://github.com/user-attachments/assets/fea30f3b-7759-48ef-91cc-58cce30d2c2c" />

- 회원가입 페이지
<img width="1042" height="485" alt="스크린샷 2026-05-03 오전 1 45 44" src="https://github.com/user-attachments/assets/6db24b45-1ec8-46c2-8529-2522b63116e9" />
<img width="1042" height="494" alt="스크린샷 2026-05-03 오전 1 46 13" src="https://github.com/user-attachments/assets/3e5409d9-cb60-4520-a04b-ac8c41a44af0" />
<img width="1042" height="494" alt="스크린샷 2026-05-03 오전 1 46 30" src="https://github.com/user-attachments/assets/f9fd00f0-bf73-4b03-9792-41b448b72b71" />


- (선택) 검색 페이지
<img width="1044" height="402" alt="스크린샷 2026-05-03 오전 2 21 28" src="https://github.com/user-attachments/assets/9425acb6-36ae-41ae-bc5f-a47eafb90298" />

