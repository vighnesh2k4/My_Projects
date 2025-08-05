function Footer(){
    return(
        <footer>
            <p>&copy; {new Date().toLocaleDateString()} Your website name</p>
        </footer>
    );
}

export default Footer;