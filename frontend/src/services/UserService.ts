import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { IResponse } from "../models/IResponse";
import {
  baseUrl,
  isJsonContentType,
  processError,
  processResponse,
} from "../utils/requestutils";
import type { User } from "../models/IUser";
import type { IUserRequest } from "../models/ICredentials";

export const userAPI = createApi({
  reducerPath: "userApi",
  baseQuery: fetchBaseQuery({
    baseUrl,
    credentials: "include",
    isJsonContentType,
  }),
  tagTypes: ["User"],
  endpoints: (builder) => ({
    fetchUser: builder.query<IResponse<User>, void>({
      query: () => ({
        url: "/profile",
        method: "GET",
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse<User>,
      transformErrorResponse: processError,
      providesTags: (result, error) => ["User"],
    }),
    loginUser: builder.mutation<IResponse<User>, IUserRequest>({
      query: (credentials) => ({
        url: "/login",
        method: "POST",
        body: credentials,
      }),
      transformResponse: processResponse<User>,
      transformErrorResponse: processError,
    }),
  }),
});
