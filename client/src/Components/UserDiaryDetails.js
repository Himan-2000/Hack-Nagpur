import React, { useEffect, useState } from 'react'
import axios from 'axios'

const UserDiaryDetails = () => {

    const [list, setList] = useState([])

    const getAllUserDiary = async () => {
        try {
            var config = {
                method: 'get',
                url: 'https://health-care-auto.herokuapp.com/api/diary/doc/clientDetail'
            };

            const response = await axios(config)

            console.log(response.data)

            setList(response.data)

        } catch (error) {
            //Ispe error toast kar dena na pl
        }
    }

    useEffect(() => {
        getAllUserDiary()
    }, [])

    return (
        <div className="container">
            {list.length !== 0 && list.map(element => (<h1>{element.text}</h1>))}
        </div>
    )
}

export default UserDiaryDetails
