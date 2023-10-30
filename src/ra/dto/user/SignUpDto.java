package ra.dto.user;

public class SignUpDto {
        private Long id;
        private String name;
        private String username;
        private String password;
        private String email;

        public SignUpDto() {
        }

        public SignUpDto(Long id, String name, String username, String password, String email) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

