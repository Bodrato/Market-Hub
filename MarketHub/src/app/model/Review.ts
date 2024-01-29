export interface Review {
    id_review: number,
    comments: string,
    rating: number,
    review_date: Date,
    id_account_buyer: number,
    id_account_seller: number
}