package ma.koursa.most_starred_github_repos;

public class Repos {

    private String repo_name,repo_description,repo_owner_name,ImageUrl;
    private int id,number_of_stars;

    public Repos(int id, String repo_name, String repo_description, String repo_owner_name, int number_of_stars, String ImageUrl) {

        this.id = id;
        this.repo_name = repo_name;
        this.repo_description = repo_description;
        this.repo_owner_name = repo_owner_name;
        this.number_of_stars = number_of_stars;
        this.ImageUrl=ImageUrl;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public void setRepo_description(String repo_description) {
        this.repo_description = repo_description;
    }

    public void setRepo_owner_name(String repo_owner_name) {
        this.repo_owner_name = repo_owner_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber_of_stars(int number_of_stars) {
        this.number_of_stars = number_of_stars;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public String getRepo_description() {
        return repo_description;
    }

    public String getRepo_owner_name() {
        return repo_owner_name;
    }

    public int getId() {
        return id;
    }

    public int getNumber_of_stars() {
        return number_of_stars;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
