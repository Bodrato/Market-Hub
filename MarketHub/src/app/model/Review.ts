export interface Review {
    idReview: number,
    comments: string,
    rating: number,
    review_date: Date,
    idAccountBuyer: number,
    idAccountSeller: number
}