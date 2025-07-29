function Amount(
    {
        numOfQuestions,
        currentQuestionIndex
    }
) {

    const sno=currentQuestionIndex+1;
    const amountButtons=[];
    let totalAmount=10000000;
   
    for(let i=0;i<numOfQuestions;i++){
        const buttonstyle={
        backgroundColor: ((numOfQuestions-i)===sno) ?'white':((numOfQuestions-i)<sno)?'green':'blue',
        border:((numOfQuestions-i)===sno)?'3px solid green':'none',
        color:((numOfQuestions-i)===sno)?'black':'white',
    };
        amountButtons.push(
            <button className="amount-btn" key={i} style={buttonstyle} >{totalAmount}</button>
        );
        totalAmount/=2;
    }

    return (
        <div style={{display:'flex',alignItems:'center',flexDirection:'column',gap:'10px'}}>
            {amountButtons}
            </div>

    );
}
export default Amount;