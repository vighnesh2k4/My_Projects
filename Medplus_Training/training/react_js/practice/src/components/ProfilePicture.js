function ProfilePicture(){
    const imageUrl="https://media.licdn.com/dms/image/v2/D5603AQEJ4BefMlXo8Q/profile-displayphoto-shrink_200_200/B56ZVoqEijGoAY-/0/1741217614678?e=2147483647&v=beta&t=a17xciO8puive8vlYb4r2v4aZDLdqnIK-MOJfHm_PBw";
    const handleClick1=(e)=>e.target.style.display="none";
    return (
        <img onClick={(e)=>handleClick1(e)} src={imageUrl} alt="profilePic"></img>
    );
}

export default ProfilePicture;