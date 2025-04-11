import React, { useState } from 'react';
import axios from 'axios';

const RatingForm = () => {
    const [rating, setRating] = useState(0);
    const [feedback, setFeedback] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const ratingData = { rating, feedback, teamId: 1 }; // Thay teamId bằng giá trị thực tế
        try {
            const response = await axios.post('http://localhost:8080/api/ratings', ratingData);
            console.log('Rating submitted:', response.data);
        } catch (error) {
            console.error('Error submitting rating:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="number"
                value={rating}
                onChange={(e) => setRating(e.target.value)}
                placeholder="Điểm đánh giá (0-5)"
                min="0"
                max="5"
            />
            <textarea
                value={feedback}
                onChange={(e) => setFeedback(e.target.value)}
                placeholder="Phản hồi"
            />
            <button type="submit">Gửi đánh giá</button>
        </form>
    );
};

export default RatingForm;